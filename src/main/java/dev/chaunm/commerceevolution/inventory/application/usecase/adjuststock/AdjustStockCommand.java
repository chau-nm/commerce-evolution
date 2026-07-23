package dev.chaunm.commerceevolution.inventory.application.usecase.adjuststock;

import java.util.UUID;

public record AdjustStockCommand(
        UUID variantId,
        int quantityDelta
) {}
