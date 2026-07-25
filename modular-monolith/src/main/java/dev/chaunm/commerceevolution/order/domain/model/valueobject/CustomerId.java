package dev.chaunm.commerceevolution.order.domain.model.valueobject;

import java.util.Objects;
import java.util.UUID;

/**
 * Order's own identifier for a customer, deliberately decoupled from the customer
 * bounded context's CustomerId type — Order must never depend on the Customer aggregate.
 */
public record CustomerId(UUID value) {

    public CustomerId {
        Objects.requireNonNull(value, "CustomerId cannot be null");
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
