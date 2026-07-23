package dev.chaunm.commerceevolution.inventory.domain.model.valueobject;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InventoryIdTest {

    @Test
    void wrapsAGivenUuid() {
        UUID value = UUID.randomUUID();

        InventoryId id = new InventoryId(value);

        assertThat(id.value()).isEqualTo(value);
        assertThat(id.toString()).isEqualTo(value.toString());
    }

    @Test
    void generateProducesADifferentIdEachTime() {
        InventoryId first = InventoryId.generate();
        InventoryId second = InventoryId.generate();

        assertThat(first).isNotEqualTo(second);
    }

    @Test
    void rejectsANullValue() {
        assertThatThrownBy(() -> new InventoryId(null))
                .isInstanceOf(NullPointerException.class);
    }
}
