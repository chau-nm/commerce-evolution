package dev.chaunm.commerceevolution.order.domain.model.valueobject;

import java.util.Objects;
import java.util.UUID;

public record OrderId(UUID value) {

    public OrderId {
        Objects.requireNonNull(value, "OrderId cannot be null");
    }

    public static OrderId generate() {
        return new OrderId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
