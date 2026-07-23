package dev.chaunm.commerceevolution.order.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.NotFoundException;

public class OrderNotFoundException extends NotFoundException {
    public OrderNotFoundException() {
        super(OrderErrorCode.ORDER_NOT_FOUND, "Order not found");
    }
}
