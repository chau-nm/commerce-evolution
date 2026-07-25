package dev.chaunm.commerceevolution.cart.application.usecase.getcart;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record GetCartResult(
        UUID cartId,
        UUID customerId,
        List<CartItemResult> items,
        Instant updatedAt
) {
    public record CartItemResult(
            UUID cartItemId,
            UUID variantId,
            int quantity
    ) {}
}
