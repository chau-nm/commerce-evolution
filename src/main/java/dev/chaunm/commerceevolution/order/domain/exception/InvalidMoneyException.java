package dev.chaunm.commerceevolution.order.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class InvalidMoneyException extends DomainException {
    public InvalidMoneyException(long amount) {
        super(OrderErrorCode.INVALID_MONEY, "Invalid amount: " + amount);
    }
}
