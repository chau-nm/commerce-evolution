package dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject;

import dev.chaunm.commerceevolution.customer.domain.exception.customer.InvalidPhoneNumberException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PhoneNumberTest {

    @ParameterizedTest
    @ValueSource(strings = {"0901234567", "+84901234567", "123456789"})
    void acceptsValidPhoneNumbers(String value) {
        PhoneNumber phoneNumber = new PhoneNumber(value);

        assertThat(phoneNumber.value()).isEqualTo(value);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"12345678", "abc1234567", "090-123-4567", "1234567890123456"})
    void rejectsInvalidPhoneNumbers(String value) {
        assertThatThrownBy(() -> new PhoneNumber(value))
                .isInstanceOf(InvalidPhoneNumberException.class);
    }
}
