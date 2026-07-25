package dev.chaunm.commerceevolution.order.infrastructure.client;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "app.payment-service")
public record PaymentServiceProperties(
        String baseUrl,
        Duration connectTimeout,
        Duration readTimeout,
        int maxRetryAttempts,
        CircuitBreaker circuitBreaker
) {
    public record CircuitBreaker(
            float failureRateThreshold,
            Duration waitDurationInOpenState,
            int slidingWindowSize
    ) {
    }
}
