package dev.chaunm.commerceevolution.payment.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.ErrorCode;

public enum PaymentErrorCode implements ErrorCode {
    PAYMENT_NOT_FOUND,
    PAYMENT_ALREADY_EXISTS,
    INVALID_STATUS_TRANSITION,
    INVALID_MONEY;

    @Override
    public String code() {
        return name();
    }
}
