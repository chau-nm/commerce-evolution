package dev.chaunm.commerceevolution.authentication.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailTest {

    @Test
    void normalizesCaseAndWhitespace() {
        Email email = new Email("  Chau.NM@Example.com ");

        assertThat(email.value()).isEqualTo("chau.nm@example.com");
    }

    @ParameterizedTest
    @ValueSource(strings = {"not-an-email", "missing-domain@", "@missing-local.com", " ", ""})
    void rejectsMalformedInput(String rawValue) {
        assertThatThrownBy(() -> new Email(rawValue))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsNull() {
        assertThatThrownBy(() -> new Email(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
