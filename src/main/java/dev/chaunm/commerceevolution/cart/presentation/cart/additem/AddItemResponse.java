package dev.chaunm.commerceevolution.cart.presentation.cart.additem;

import java.util.UUID;

public record AddItemResponse(
        UUID cartId,
        UUID cartItemId,
        UUID variantId,
        int quantity
) {}
