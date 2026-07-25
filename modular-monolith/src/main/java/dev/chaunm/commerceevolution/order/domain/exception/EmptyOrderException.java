package dev.chaunm.commerceevolution.order.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class EmptyOrderException extends DomainException {
    public EmptyOrderException() {
        super(OrderErrorCode.EMPTY_ORDER, "Cannot place an order from an empty cart");
    }
}
