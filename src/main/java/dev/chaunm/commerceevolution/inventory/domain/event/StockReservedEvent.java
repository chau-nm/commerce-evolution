package dev.chaunm.commerceevolution.inventory.domain.event;

import dev.chaunm.commerceevolution.inventory.domain.model.valueobject.InventoryId;
import dev.chaunm.commerceevolution.inventory.domain.model.valueobject.VariantId;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEvent;

public record StockReservedEvent(
        InventoryId inventoryId,
        VariantId variantId,
        int quantity,
        int remainingAvailable
) implements DomainEvent {
}
