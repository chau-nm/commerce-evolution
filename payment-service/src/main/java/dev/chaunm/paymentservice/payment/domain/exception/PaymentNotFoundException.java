package dev.chaunm.paymentservice.payment.domain.exception;

import dev.chaunm.paymentservice.shared.domain.exception.NotFoundException;

public class PaymentNotFoundException extends NotFoundException {
    public PaymentNotFoundException() {
        super(PaymentErrorCode.PAYMENT_NOT_FOUND, "Payment not found");
    }
}
