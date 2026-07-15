package dev.chaunm.commerceevolution.inventory.application.usecase.createinventory;

import java.util.UUID;

public record CreateInventoryResult(
        UUID inventoryId,
        UUID variantId,
        int availableQuantity,
        int reservedQuantity
) {}
