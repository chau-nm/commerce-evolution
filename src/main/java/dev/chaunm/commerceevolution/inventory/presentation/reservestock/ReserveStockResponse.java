package dev.chaunm.commerceevolution.inventory.presentation.reservestock;

import java.util.UUID;

public record ReserveStockResponse(
        UUID inventoryId,
        UUID variantId,
        int availableQuantity,
        int reservedQuantity
) {}
