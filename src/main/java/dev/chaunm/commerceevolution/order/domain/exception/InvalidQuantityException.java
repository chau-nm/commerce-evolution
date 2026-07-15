package dev.chaunm.commerceevolution.order.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class InvalidQuantityException extends DomainException {
    public InvalidQuantityException(int quantity) {
        super(OrderErrorCode.INVALID_QUANTITY, "Invalid quantity: " + quantity);
    }
}
