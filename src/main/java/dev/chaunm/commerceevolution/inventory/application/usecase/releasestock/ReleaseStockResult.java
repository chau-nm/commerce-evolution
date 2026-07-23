package dev.chaunm.commerceevolution.inventory.application.usecase.releasestock;

import java.util.UUID;

public record ReleaseStockResult(
        UUID inventoryId,
        UUID variantId,
        int availableQuantity,
        int reservedQuantity
) {}
