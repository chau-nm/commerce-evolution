package dev.chaunm.commerceevolution.inventory.presentation.deductstock;

import java.util.UUID;

public record DeductStockResponse(
        UUID inventoryId,
        UUID variantId,
        int availableQuantity,
        int reservedQuantity
) {}
