package dev.chaunm.commerceevolution.catalog.domain.model.brand.valueobject;

import dev.chaunm.commerceevolution.catalog.domain.exception.brand.InvalidBrandNameException;

public record BrandName(String value) {

    public static final int MAX_LENGTH = 255;

    public BrandName {
        if (value == null || value.isBlank() || value.length() > MAX_LENGTH) {
            throw new InvalidBrandNameException(value);
        }
    }
}
