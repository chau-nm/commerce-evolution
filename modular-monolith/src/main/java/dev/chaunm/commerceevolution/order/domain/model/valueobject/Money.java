package dev.chaunm.commerceevolution.order.domain.model.valueobject;

import dev.chaunm.commerceevolution.order.domain.exception.InvalidMoneyException;

public record Money(long amount) {

    public Money {
        if (amount < 0) {
            throw new InvalidMoneyException(amount);
        }
    }

    public Money add(Money other) {
        return new Money(this.amount + other.amount);
    }

    public Money multiply(int factor) {
        return new Money(this.amount * factor);
    }
}
