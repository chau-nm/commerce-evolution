package dev.chaunm.commerceevolution.inventory.application.usecase.getinventory;

import java.util.UUID;

public record GetInventoryResult(
        UUID inventoryId,
        UUID variantId,
        int availableQuantity,
        int reservedQuantity
) {}
