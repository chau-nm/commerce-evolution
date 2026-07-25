package dev.chaunm.paymentservice.payment.infrastructure.client;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.net.http.HttpClient;
import java.time.Duration;

/**
 * Wires the resilience policy (timeout + retry + circuit breaker) for every outbound call
 * payment-service makes to order-service. Kept as plain resilience4j-core beans (not the
 * resilience4j-spring-boot3 starter/annotations) so this has no dependency on which Spring Boot
 * major version is in play — the decorators are applied explicitly in
 * {@link PaymentEventNotifierImpl}.
 */
@Configuration
public class OrderServiceClientConfig {

    @Bean
    public RestClient orderServiceRestClient(OrderServiceProperties properties) {
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(properties.connectTimeout())
                .build();
        JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory(httpClient);
        requestFactory.setReadTimeout(properties.readTimeout());

        return RestClient.builder()
                .baseUrl(properties.baseUrl())
                .requestFactory(requestFactory)
                .build();
    }

    @Bean
    public CircuitBreaker orderServiceCircuitBreaker(OrderServiceProperties properties) {
        OrderServiceProperties.CircuitBreaker cb = properties.circuitBreaker();
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(cb.failureRateThreshold())
                .waitDurationInOpenState(cb.waitDurationInOpenState())
                .slidingWindowSize(cb.slidingWindowSize())
                .recordException(this::isRetryableOrCircuitTrippingFailure)
                .build();
        return CircuitBreaker.of("order-service", config);
    }

    @Bean
    public Retry orderServiceRetry(OrderServiceProperties properties) {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(properties.maxRetryAttempts())
                .waitDuration(Duration.ofMillis(300))
                .retryExceptions(IOException.class, java.util.concurrent.TimeoutException.class,
                        org.springframework.web.client.ResourceAccessException.class,
                        org.springframework.web.client.HttpServerErrorException.class)
                .build();
        return Retry.of("order-service", config);
    }

    private boolean isRetryableOrCircuitTrippingFailure(Throwable throwable) {
        // A 409 (already processed) or 404 is a legitimate business outcome, not an
        // infrastructure failure — must not trip the circuit breaker or trigger a retry.
        if (throwable instanceof org.springframework.web.client.HttpClientErrorException) {
            return false;
        }
        return true;
    }
}
