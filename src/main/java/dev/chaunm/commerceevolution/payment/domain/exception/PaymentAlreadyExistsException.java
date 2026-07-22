package dev.chaunm.commerceevolution.payment.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.ConflictException;

public class PaymentAlreadyExistsException extends ConflictException {
    public PaymentAlreadyExistsException() {
        super(PaymentErrorCode.PAYMENT_ALREADY_EXISTS, "Payment already initiated for this order");
    }
}
