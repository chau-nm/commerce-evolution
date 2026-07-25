package dev.chaunm.paymentservice.payment.domain.model.valueobject;

import java.util.Objects;
import java.util.UUID;

/**
 * Payment's own identifier for an order, deliberately decoupled from the order service's own
 * Order aggregate — payment-service has no dependency on order-service internals at all.
 * Correlated only through the UUID value carried in the initiate-payment request and passed
 * back on outbound calls to order-service's public HTTP API.
 */
public record OrderId(UUID value) {

    public OrderId {
        Objects.requireNonNull(value, "OrderId cannot be null");
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
