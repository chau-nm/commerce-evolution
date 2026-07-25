package dev.chaunm.commerceevolution.inventory.domain.factory;

import dev.chaunm.commerceevolution.inventory.domain.event.InventoryCreatedEvent;
import dev.chaunm.commerceevolution.inventory.domain.exception.InvalidQuantityException;
import dev.chaunm.commerceevolution.inventory.domain.model.Inventory;
import dev.chaunm.commerceevolution.inventory.domain.model.valueobject.VariantId;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InventoryFactoryTest {

    @Test
    void createsAnInventoryWithZeroReservedQuantityAndRegistersAnEvent() {
        VariantId variantId = new VariantId(UUID.randomUUID());

        Inventory inventory = InventoryFactory.create(variantId, 10);

        assertThat(inventory.getId()).isNotNull();
        assertThat(inventory.getVariantId()).isEqualTo(variantId);
        assertThat(inventory.getAvailableQuantity()).isEqualTo(10);
        assertThat(inventory.getReservedQuantity()).isZero();
        assertThat(inventory.domainEvents())
                .singleElement()
                .isInstanceOf(InventoryCreatedEvent.class);
    }

    @Test
    void allowsCreatingAnInventoryWithZeroInitialQuantity() {
        Inventory inventory = InventoryFactory.create(new VariantId(UUID.randomUUID()), 0);

        assertThat(inventory.getAvailableQuantity()).isZero();
    }

    @Test
    void rejectsANegativeInitialQuantity() {
        VariantId variantId = new VariantId(UUID.randomUUID());

        assertThatThrownBy(() -> InventoryFactory.create(variantId, -1))
                .isInstanceOf(InvalidQuantityException.class);
    }
}
