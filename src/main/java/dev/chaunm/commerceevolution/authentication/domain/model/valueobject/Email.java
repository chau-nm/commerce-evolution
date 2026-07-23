package dev.chaunm.commerceevolution.authentication.domain.model.valueobject;

import dev.chaunm.commerceevolution.authentication.domain.exception.InvalidEmailException;

import java.util.Objects;

public record Email(String value) {
    public static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public Email {
        if (
                value == null || value.isBlank() ||
                !value.matches(EMAIL_PATTERN)
        ) {
            throw new InvalidEmailException(value);
        }
    }
}
