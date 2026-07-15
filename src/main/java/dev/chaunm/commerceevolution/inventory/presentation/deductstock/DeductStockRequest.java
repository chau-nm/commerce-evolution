package dev.chaunm.commerceevolution.inventory.presentation.deductstock;

import jakarta.validation.constraints.Min;

public record DeductStockRequest(
        @Min(1)
        int quantity
) {}
