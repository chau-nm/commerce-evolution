package dev.chaunm.commerceevolution.inventory.presentation.createinventory;

import java.util.UUID;

public record CreateInventoryResponse(
        UUID inventoryId,
        UUID variantId,
        int availableQuantity,
        int reservedQuantity
) {}
