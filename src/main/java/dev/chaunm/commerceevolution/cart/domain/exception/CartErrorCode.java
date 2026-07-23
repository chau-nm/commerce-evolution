package dev.chaunm.commerceevolution.cart.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.ErrorCode;

public enum CartErrorCode implements ErrorCode {
    CART_NOT_FOUND,
    CART_ITEM_NOT_FOUND,
    INVALID_QUANTITY,
    CUSTOMER_NOT_FOUND;

    @Override
    public String code() {
        return name();
    }
}
