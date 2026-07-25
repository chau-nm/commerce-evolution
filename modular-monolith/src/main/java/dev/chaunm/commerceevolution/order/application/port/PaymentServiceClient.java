package dev.chaunm.commerceevolution.order.application.port;

import java.util.UUID;

/**
 * Outbound port to the standalone payment-service: the only coupling point from order to
 * payment now that payment is a separate process. Fire-and-forget from order's point of view —
 * a failure here must never fail order placement, so the adapter swallows/logs errors after
 * exhausting its resilience policy rather than throwing.
 */
public interface PaymentServiceClient {
    void initiatePayment(UUID orderId, long amount);
}
