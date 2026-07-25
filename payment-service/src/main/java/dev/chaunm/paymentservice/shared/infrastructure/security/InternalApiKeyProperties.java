package dev.chaunm.paymentservice.shared.infrastructure.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The shared secret payment-service and order-service present to each other for
 * service-to-service calls. Neither side has a user-facing auth model of its own yet (payment
 * has no login; order's calls into payment/payment's calls into order are never made on behalf
 * of an end user), so a single static header is the pragmatic minimum viable control — see the
 * migration report for the recommended follow-up (verify-only JWT using the shared RSA public
 * key, since "Authentication contracts" is explicitly a true shared concept).
 */
@ConfigurationProperties(prefix = "app.internal")
public record InternalApiKeyProperties(String apiKey) {
}
