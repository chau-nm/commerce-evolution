package dev.chaunm.commerceevolution.authentication.domain.model.valueobject;

import dev.chaunm.commerceevolution.authentication.domain.exception.InvalidPasswordException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HashPasswordTest {

    @Test
    void acceptsANonBlankHashedValue() {
        HashPassword hashPassword = new HashPassword("$2a$10$hashedvalue");

        assertThat(hashPassword.value()).isEqualTo("$2a$10$hashedvalue");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void rejectsBlankOrNullValues(String value) {
        assertThatThrownBy(() -> new HashPassword(value))
                .isInstanceOf(InvalidPasswordException.class);
    }
}
