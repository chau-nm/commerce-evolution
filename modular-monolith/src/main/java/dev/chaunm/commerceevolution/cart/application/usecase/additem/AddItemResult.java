package dev.chaunm.commerceevolution.cart.application.usecase.additem;

import java.util.UUID;

public record AddItemResult(
        UUID cartId,
        UUID cartItemId,
        UUID variantId,
        int quantity
) {}
