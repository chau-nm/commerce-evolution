package dev.chaunm.commerceevolution.order.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.ErrorCode;

public enum OrderErrorCode implements ErrorCode {
    ORDER_NOT_FOUND,
    EMPTY_ORDER,
    VARIANT_NOT_FOUND,
    INSUFFICIENT_STOCK,
    INVALID_STATUS_TRANSITION,
    INVALID_QUANTITY,
    INVALID_MONEY,
    INVALID_ORDER_NUMBER,
    INVALID_SHIPPING_ADDRESS,
    CUSTOMER_NOT_FOUND;

    @Override
    public String code() {
        return name();
    }
}
