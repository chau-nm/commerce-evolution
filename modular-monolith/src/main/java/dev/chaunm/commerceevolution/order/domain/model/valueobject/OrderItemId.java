package dev.chaunm.commerceevolution.order.domain.model.valueobject;

import java.util.Objects;
import java.util.UUID;

public record OrderItemId(UUID value) {

    public OrderItemId {
        Objects.requireNonNull(value, "OrderItemId cannot be null");
    }

    public static OrderItemId generate() {
        return new OrderItemId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
