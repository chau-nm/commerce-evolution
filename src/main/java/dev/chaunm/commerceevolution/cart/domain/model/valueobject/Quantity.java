package dev.chaunm.commerceevolution.cart.domain.model.valueobject;

import dev.chaunm.commerceevolution.cart.domain.exception.InvalidQuantityException;

public record Quantity(int value) {

    public Quantity {
        if (value <= 0) {
            throw new InvalidQuantityException(value);
        }
    }

    public Quantity add(Quantity other) {
        return new Quantity(this.value + other.value);
    }
}
