package dev.chaunm.commerceevolution.inventory.application.usecase.createinventory;

import java.util.UUID;

public record CreateInventoryCommand(
        UUID variantId,
        int initialQuantity
) {}
