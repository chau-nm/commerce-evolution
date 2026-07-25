package dev.chaunm.commerceevolution.inventory.application.usecase.releasestock;

import java.util.UUID;

public record ReleaseStockCommand(
        UUID variantId,
        int quantity
) {}
