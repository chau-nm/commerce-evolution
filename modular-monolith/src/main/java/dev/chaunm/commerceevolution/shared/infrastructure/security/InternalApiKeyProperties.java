package dev.chaunm.commerceevolution.shared.infrastructure.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The shared secret order-service (this app) and payment-service present to each other for
 * service-to-service calls: payment-service calling {@code POST /internal/payment-events} or
 * {@code PUT /api/v1/orders/{orderId}/pay}, and this app calling payment-service's
 * {@code POST /api/v1/payments}. Neither of those endpoints is meant to be reachable by an end
 * user, so a single static header is the pragmatic minimum viable control — see the migration
 * report for the recommended follow-up (verify-only JWT using the shared RSA public key).
 */
@ConfigurationProperties(prefix = "app.internal")
public record InternalApiKeyProperties(String apiKey) {
}
