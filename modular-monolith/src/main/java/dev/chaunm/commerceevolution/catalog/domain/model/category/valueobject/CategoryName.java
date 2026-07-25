package dev.chaunm.commerceevolution.catalog.domain.model.category.valueobject;

import dev.chaunm.commerceevolution.catalog.domain.exception.category.InvalidCategoryNameException;

public record CategoryName(String value) {

    public static final int MAX_LENGTH = 255;

    public CategoryName {
        if (value == null || value.isBlank() || value.length() > MAX_LENGTH) {
            throw new InvalidCategoryNameException(value);
        }
    }
}
