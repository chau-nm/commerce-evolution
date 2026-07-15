package dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject;

import dev.chaunm.commerceevolution.customer.domain.exception.customer.InvalidPhoneNumberException;

public record PhoneNumber(String value) {

    public static final String PHONE_PATTERN = "^\\+?[0-9]{9,15}$";

    public PhoneNumber {
        if (value == null || !value.matches(PHONE_PATTERN)) {
            throw new InvalidPhoneNumberException(value);
        }
    }
}
