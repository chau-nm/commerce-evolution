package dev.chaunm.paymentservice.payment.infrastructure.client;

import java.util.UUID;

/** Body posted to order-service's {@code POST /internal/payment-events} endpoint. */
public record PaymentEventWebhookRequest(
        UUID paymentId,
        UUID orderId,
        String status
) {
}
