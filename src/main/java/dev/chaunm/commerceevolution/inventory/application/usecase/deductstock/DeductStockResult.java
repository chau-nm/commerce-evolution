package dev.chaunm.commerceevolution.inventory.application.usecase.deductstock;

import java.util.UUID;

public record DeductStockResult(
        UUID inventoryId,
        UUID variantId,
        int availableQuantity,
        int reservedQuantity
) {}
