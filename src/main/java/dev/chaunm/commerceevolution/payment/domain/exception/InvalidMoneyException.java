package dev.chaunm.commerceevolution.payment.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class InvalidMoneyException extends DomainException {
    public InvalidMoneyException(long amount) {
        super(PaymentErrorCode.INVALID_MONEY, "Invalid amount: " + amount);
    }
}
