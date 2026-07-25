package dev.chaunm.commerceevolution.cart.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class InvalidQuantityException extends DomainException {
    public InvalidQuantityException(int quantity) {
        super(CartErrorCode.INVALID_QUANTITY, "Invalid quantity: " + quantity);
    }
}
