package dev.chaunm.commerceevolution.customer.domain.exception.customer;

import dev.chaunm.commerceevolution.customer.domain.exception.CustomerErrorCode;
import dev.chaunm.commerceevolution.shared.domain.exception.NotFoundException;

public class CustomerNotFoundException extends NotFoundException {
    public CustomerNotFoundException() {
        super(CustomerErrorCode.CUSTOMER_NOT_FOUND, "Customer not found");
    }
}
