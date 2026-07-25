package dev.chaunm.commerceevolution.inventory.application.usecase.deductstock;

import java.util.UUID;

public record DeductStockCommand(
        UUID variantId,
        int quantity
) {}
