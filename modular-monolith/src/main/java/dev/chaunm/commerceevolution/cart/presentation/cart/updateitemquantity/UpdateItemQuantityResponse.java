package dev.chaunm.commerceevolution.cart.presentation.cart.updateitemquantity;

import java.util.UUID;

public record UpdateItemQuantityResponse(
        UUID cartId,
        UUID cartItemId,
        UUID variantId,
        int quantity,
        boolean removed
) {}
