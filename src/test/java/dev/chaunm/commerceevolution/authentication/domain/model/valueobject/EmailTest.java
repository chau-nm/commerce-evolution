package dev.chaunm.commerceevolution.authentication.domain.model.valueobject;

import dev.chaunm.commerceevolution.authentication.domain.exception.InvalidEmailException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailTest {

    @Test
    void acceptsAValidEmail() {
        Email email = new Email("nguyen.van.a@example.com");

        assertThat(email.value()).isEqualTo("nguyen.van.a@example.com");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "not-an-email", "missing-domain@", "@missing-local.com"})
    void rejectsBlankOrMalformedEmails(String value) {
        assertThatThrownBy(() -> new Email(value))
                .isInstanceOf(InvalidEmailException.class);
    }
}
