package dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject;

import dev.chaunm.commerceevolution.customer.domain.exception.customer.InvalidFullNameException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FullNameTest {

    @Test
    void acceptsAValidName() {
        FullName fullName = new FullName("Nguyen Van A");

        assertThat(fullName.value()).isEqualTo("Nguyen Van A");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void rejectsBlankOrNullNames(String value) {
        assertThatThrownBy(() -> new FullName(value))
                .isInstanceOf(InvalidFullNameException.class);
    }

    @Test
    void rejectsNamesLongerThan255Characters() {
        String tooLong = "a".repeat(256);

        assertThatThrownBy(() -> new FullName(tooLong))
                .isInstanceOf(InvalidFullNameException.class);
    }
}
