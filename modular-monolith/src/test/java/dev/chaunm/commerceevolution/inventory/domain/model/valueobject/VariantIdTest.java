package dev.chaunm.commerceevolution.inventory.domain.model.valueobject;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VariantIdTest {

    @Test
    void wrapsAGivenUuid() {
        UUID value = UUID.randomUUID();

        VariantId id = new VariantId(value);

        assertThat(id.value()).isEqualTo(value);
        assertThat(id.toString()).isEqualTo(value.toString());
    }

    @Test
    void rejectsANullValue() {
        assertThatThrownBy(() -> new VariantId(null))
                .isInstanceOf(NullPointerException.class);
    }
}
