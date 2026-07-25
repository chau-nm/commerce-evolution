package dev.chaunm.commerceevolution.order.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.NotFoundException;

public class CustomerNotFoundException extends NotFoundException {
    public CustomerNotFoundException() {
        super(OrderErrorCode.CUSTOMER_NOT_FOUND, "Customer not found for the current account");
    }
}
