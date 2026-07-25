package dev.chaunm.paymentservice.payment.domain.exception;

import dev.chaunm.paymentservice.shared.domain.exception.ConflictException;

public class PaymentAlreadyExistsException extends ConflictException {
    public PaymentAlreadyExistsException() {
        super(PaymentErrorCode.PAYMENT_ALREADY_EXISTS, "Payment already initiated for this order");
    }
}
