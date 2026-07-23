package dev.chaunm.commerceevolution.payment.domain.model.valueobject;

import java.util.Objects;
import java.util.UUID;

/**
 * Payment's own identifier for an order, deliberately decoupled from the order bounded
 * context's OrderId type — Payment must never depend on the Order aggregate. Correlated only
 * through the UUID value carried in OrderCreatedEvent and passed back on the public
 * MarkOrderPaidUseCase/CancelOrderUseCase APIs.
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
