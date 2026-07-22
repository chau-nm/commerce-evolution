package dev.chaunm.commerceevolution.cart.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.NotFoundException;

public class CustomerNotFoundException extends NotFoundException {
    public CustomerNotFoundException() {
        super(CartErrorCode.CUSTOMER_NOT_FOUND, "Customer not found for the current account");
    }
}
