package dev.chaunm.commerceevolution.payment.domain.model.valueobject;

import dev.chaunm.commerceevolution.payment.domain.exception.InvalidMoneyException;

public record Money(long amount) {

    public Money {
        if (amount <= 0) {
            throw new InvalidMoneyException(amount);
        }
    }
}
