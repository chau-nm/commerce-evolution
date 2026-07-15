package dev.chaunm.commerceevolution.cart.presentation.cart.updateitemquantity;

import jakarta.validation.constraints.Min;

public record UpdateItemQuantityRequest(
        @Min(0)
        int quantity
) {}
