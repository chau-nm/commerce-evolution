package dev.chaunm.commerceevolution.cart.domain.model.valueobject;

import java.util.Objects;
import java.util.UUID;

public record CartItemId(UUID value) {

    public CartItemId {
        Objects.requireNonNull(value, "CartItemId cannot be null");
    }

    public static CartItemId generate() {
        return new CartItemId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
