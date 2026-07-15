package dev.chaunm.commerceevolution.inventory.presentation.createinventory;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateInventoryRequest(
        @NotNull
        UUID variantId,
        @Min(0)
        int initialQuantity
) {}
