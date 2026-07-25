package dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject;

import dev.chaunm.commerceevolution.customer.domain.exception.customer.InvalidFullNameException;

public record FullName(String value) {

    private static final int MAX_LENGTH = 255;

    public FullName {
        if (value == null || value.isBlank() || value.length() > MAX_LENGTH) {
            throw new InvalidFullNameException(value);
        }
    }
}
