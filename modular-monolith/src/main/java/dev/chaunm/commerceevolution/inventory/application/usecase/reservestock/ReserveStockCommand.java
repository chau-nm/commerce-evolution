package dev.chaunm.commerceevolution.inventory.application.usecase.reservestock;

import java.util.UUID;

public record ReserveStockCommand(
        UUID variantId,
        int quantity
) {}
