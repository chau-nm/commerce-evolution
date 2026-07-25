package dev.chaunm.commerceevolution.inventory.presentation.getinventory;

import java.util.UUID;

public record GetInventoryResponse(
        UUID inventoryId,
        UUID variantId,
        int availableQuantity,
        int reservedQuantity
) {}
