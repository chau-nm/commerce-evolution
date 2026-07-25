package dev.chaunm.commerceevolution.order.domain.exception;

import dev.chaunm.commerceevolution.order.domain.model.valueobject.OrderStatus;
import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class InvalidOrderStatusTransitionException extends DomainException {
    public InvalidOrderStatusTransitionException(OrderStatus from, OrderStatus to) {
        super(
                OrderErrorCode.INVALID_STATUS_TRANSITION,
                "Cannot transition order from " + from + " to " + to
        );
    }
}
