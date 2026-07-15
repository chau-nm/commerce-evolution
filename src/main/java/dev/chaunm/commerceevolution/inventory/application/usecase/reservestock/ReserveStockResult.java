package dev.chaunm.commerceevolution.inventory.application.usecase.reservestock;

import java.util.UUID;

public record ReserveStockResult(
        UUID inventoryId,
        UUID variantId,
        int availableQuantity,
        int reservedQuantity
) {}
