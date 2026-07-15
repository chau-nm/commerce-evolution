package dev.chaunm.commerceevolution.inventory.presentation.releasestock;

import java.util.UUID;

public record ReleaseStockResponse(
        UUID inventoryId,
        UUID variantId,
        int availableQuantity,
        int reservedQuantity
) {}
