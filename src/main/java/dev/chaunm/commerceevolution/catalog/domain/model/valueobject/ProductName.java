package dev.chaunm.commerceevolution.catalog.domain.model.valueobject;

import dev.chaunm.commerceevolution.catalog.domain.exception.InvalidProductNameException;

public record ProductName(String value) {

    public static final int MAX_LENGTH = 255;

    public ProductName {
        if (value == null || value.isBlank() || value.length() > MAX_LENGTH) {
            throw new InvalidProductNameException(value);
        }
    }
}
