package dev.chaunm.commerceevolution.cart.presentation.cart.getcart;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record GetCartResponse(
        UUID cartId,
        UUID customerId,
        List<CartItemResponse> items,
        Instant updatedAt
) {
    public record CartItemResponse(
            UUID cartItemId,
            UUID variantId,
            int quantity
    ) {}
}
