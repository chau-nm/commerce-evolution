package dev.chaunm.commerceevolution.customer.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.ErrorCode;

public enum CustomerErrorCode implements ErrorCode {
    INVALID_FULL_NAME,
    INVALID_PHONE_NUMBER,
    ADDRESS_NOT_FOUND,
    DEFAULT_ADDRESS_REMOVAL_NOT_ALLOWED,
    CUSTOMER_NOT_FOUND,
    DUPLICATE_CUSTOMER_FOR_ACCOUNT;

    @Override
    public String code() {
        return name();
    }
}
