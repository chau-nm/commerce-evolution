package dev.chaunm.commerceevolution.order.infrastructure.client;

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
 * order makes to payment-service. Plain resilience4j-core beans, applied explicitly in
 * {@link PaymentServiceClientImpl} — no dependency on a specific Spring Boot version's
 * resilience4j-spring-boot starter.
 */
@Configuration
public class PaymentServiceClientConfig {

    @Bean
    public RestClient paymentServiceRestClient(PaymentServiceProperties properties) {
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
    public CircuitBreaker paymentServiceCircuitBreaker(PaymentServiceProperties properties) {
        PaymentServiceProperties.CircuitBreaker cb = properties.circuitBreaker();
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(cb.failureRateThreshold())
                .waitDurationInOpenState(cb.waitDurationInOpenState())
                .slidingWindowSize(cb.slidingWindowSize())
                .recordException(throwable -> !(throwable instanceof org.springframework.web.client.HttpClientErrorException))
                .build();
        return CircuitBreaker.of("payment-service", config);
    }

    @Bean
    public Retry paymentServiceRetry(PaymentServiceProperties properties) {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(properties.maxRetryAttempts())
                .waitDuration(Duration.ofMillis(300))
                .retryExceptions(IOException.class, java.util.concurrent.TimeoutException.class,
                        org.springframework.web.client.ResourceAccessException.class,
                        org.springframework.web.client.HttpServerErrorException.class)
                .build();
        return Retry.of("payment-service", config);
    }
}
