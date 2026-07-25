package dev.chaunm.commerceevolution.customer.domain.exception.customer;

import dev.chaunm.commerceevolution.customer.domain.exception.CustomerErrorCode;
import dev.chaunm.commerceevolution.shared.domain.exception.ConflictException;

public class DuplicateCustomerForAccountException extends ConflictException {
    public DuplicateCustomerForAccountException() {
        super(CustomerErrorCode.DUPLICATE_CUSTOMER_FOR_ACCOUNT, "This account already has a customer profile");
    }
}
