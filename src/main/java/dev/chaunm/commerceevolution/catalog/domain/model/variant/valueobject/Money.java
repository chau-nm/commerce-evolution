package dev.chaunm.commerceevolution.catalog.domain.model.variant.valueobject;

import dev.chaunm.commerceevolution.catalog.domain.exception.variant.InvalidMoneyException;

public record Money(long amount) {

    public Money {
        if (amount <= 0) {
            throw new InvalidMoneyException(amount);
        }
    }
}
