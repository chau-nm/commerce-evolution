package dev.chaunm.paymentservice.payment.domain.exception;

import dev.chaunm.paymentservice.shared.domain.exception.DomainException;

public class InvalidMoneyException extends DomainException {
    public InvalidMoneyException(long amount) {
        super(PaymentErrorCode.INVALID_MONEY, "Invalid amount: " + amount);
    }
}
