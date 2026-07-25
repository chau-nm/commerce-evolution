package dev.chaunm.commerceevolution.authentication.domain.model.valueobject;

import dev.chaunm.commerceevolution.authentication.domain.exception.InvalidPasswordException;

import java.util.Objects;

public record HashPassword(String value) {

    public HashPassword {
        if (value == null || value.isBlank()) {
            throw new InvalidPasswordException();
        }
    }
}
