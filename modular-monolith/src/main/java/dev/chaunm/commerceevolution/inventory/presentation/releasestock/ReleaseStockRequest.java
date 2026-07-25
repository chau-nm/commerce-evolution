package dev.chaunm.commerceevolution.inventory.presentation.releasestock;

import jakarta.validation.constraints.Min;

public record ReleaseStockRequest(
        @Min(1)
        int quantity
) {}
