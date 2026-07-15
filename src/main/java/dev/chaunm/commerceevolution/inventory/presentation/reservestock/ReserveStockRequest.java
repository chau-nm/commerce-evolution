package dev.chaunm.commerceevolution.inventory.presentation.reservestock;

import jakarta.validation.constraints.Min;

public record ReserveStockRequest(
        @Min(1)
        int quantity
) {}
