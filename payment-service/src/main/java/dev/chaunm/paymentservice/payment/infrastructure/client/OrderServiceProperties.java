package dev.chaunm.paymentservice.payment.infrastructure.client;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "app.order-service")
public record OrderServiceProperties(
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
