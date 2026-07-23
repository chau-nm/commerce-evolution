package dev.chaunm.commerceevolution.inventory.application.usecase.adjuststock;

import java.util.UUID;

public record AdjustStockResult(
        UUID inventoryId,
        UUID variantId,
        int availableQuantity,
        int reservedQuantity
) {}
