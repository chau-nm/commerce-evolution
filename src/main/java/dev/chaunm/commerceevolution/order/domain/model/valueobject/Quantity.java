package dev.chaunm.commerceevolution.order.domain.model.valueobject;

import dev.chaunm.commerceevolution.order.domain.exception.InvalidQuantityException;

public record Quantity(int value) {

    public Quantity {
        if (value <= 0) {
            throw new InvalidQuantityException(value);
        }
    }
}
