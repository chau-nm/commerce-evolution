package dev.chaunm.commerceevolution.inventory.presentation.adjuststock;

import java.util.UUID;

public record AdjustStockResponse(
        UUID inventoryId,
        UUID variantId,
        int availableQuantity,
        int reservedQuantity
) {}
