package dev.chaunm.commerceevolution.catalog.domain.model.valueobject;

import dev.chaunm.commerceevolution.catalog.domain.exception.InvalidSlugException;

public record Slug(String value) {

    public static final String SLUG_PATTERN = "^[a-z0-9]+(?:-[a-z0-9]+)*$";
    public static final int MAX_LENGTH = 255;

    public Slug {
        if (
                value == null || value.isBlank() ||
                value.length() > MAX_LENGTH ||
                !value.matches(SLUG_PATTERN)
        ) {
            throw new InvalidSlugException(value);
        }
    }
}
