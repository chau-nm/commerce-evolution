package dev.chaunm.paymentservice.payment.application.port;

import dev.chaunm.paymentservice.payment.domain.model.valueobject.OrderId;
import dev.chaunm.paymentservice.payment.domain.model.valueobject.PaymentId;
import dev.chaunm.paymentservice.payment.domain.model.valueobject.PaymentStatus;

/**
 * Outbound port to order-service: the only coupling point from payment-service to the rest of
 * the platform. Used to report a settled payment's outcome so order-service can mark the order
 * paid (and downstream, notify the customer) without payment-service knowing anything about
 * order's aggregate, notification's use cases, or how those reactions are wired — it just
 * reports a fact over HTTP. The infrastructure adapter is responsible for timeout/retry/circuit
 * breaking; this port stays a plain synchronous call.
 */
public interface PaymentEventNotifier {
    void notifyOutcome(PaymentId paymentId, OrderId orderId, PaymentStatus status);
}
