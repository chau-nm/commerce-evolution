package dev.chaunm.commerceevolution.inventory.domain.factory;

import dev.chaunm.commerceevolution.inventory.domain.event.InventoryCreatedEvent;
import dev.chaunm.commerceevolution.inventory.domain.exception.InvalidQuantityException;
import dev.chaunm.commerceevolution.inventory.domain.model.Inventory;
import dev.chaunm.commerceevolution.inventory.domain.model.valueobject.InventoryId;
import dev.chaunm.commerceevolution.inventory.domain.model.valueobject.VariantId;

public class InventoryFactory {

    public static Inventory create(VariantId variantId, int initialQuantity) {
        if (initialQuantity < 0) {
            throw new InvalidQuantityException(initialQuantity);
        }

        Inventory inventory = new Inventory(InventoryId.generate(), variantId, initialQuantity, 0);
        inventory.registerEvent(new InventoryCreatedEvent(inventory.getId(), inventory.getVariantId(), initialQuantity));

        return inventory;
    }
}
