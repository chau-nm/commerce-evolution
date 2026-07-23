package dev.chaunm.commerceevolution.customer.domain.exception.customer;

import dev.chaunm.commerceevolution.customer.domain.exception.CustomerErrorCode;
import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class InvalidPhoneNumberException extends DomainException {
    public InvalidPhoneNumberException(String value) {
        super(CustomerErrorCode.INVALID_PHONE_NUMBER, "Invalid phone number: " + value);
    }
}
