package dev.chaunm.commerceevolution.payment.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.NotFoundException;

public class PaymentNotFoundException extends NotFoundException {
    public PaymentNotFoundException() {
        super(PaymentErrorCode.PAYMENT_NOT_FOUND, "Payment not found");
    }
}
