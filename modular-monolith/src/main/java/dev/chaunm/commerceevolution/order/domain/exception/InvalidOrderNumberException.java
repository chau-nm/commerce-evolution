package dev.chaunm.commerceevolution.order.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class InvalidOrderNumberException extends DomainException {
    public InvalidOrderNumberException(String value) {
        super(OrderErrorCode.INVALID_ORDER_NUMBER, "Invalid order number: " + value);
    }
}
