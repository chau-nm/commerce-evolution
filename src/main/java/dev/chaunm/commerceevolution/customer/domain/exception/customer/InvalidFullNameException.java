package dev.chaunm.commerceevolution.customer.domain.exception.customer;

import dev.chaunm.commerceevolution.customer.domain.exception.CustomerErrorCode;
import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class InvalidFullNameException extends DomainException {
    public InvalidFullNameException(String value) {
        super(CustomerErrorCode.INVALID_FULL_NAME, "Invalid full name: " + value);
    }
}
