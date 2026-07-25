package dev.chaunm.commerceevolution.cart.application.usecase.updateitemquantity;

import java.util.UUID;

public record UpdateItemQuantityResult(
        UUID cartId,
        UUID cartItemId,
        UUID variantId,
        int quantity,
        boolean removed
) {}
