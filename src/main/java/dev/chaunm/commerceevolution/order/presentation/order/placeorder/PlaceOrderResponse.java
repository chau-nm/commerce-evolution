package dev.chaunm.commerceevolution.order.presentation.order.placeorder;

import java.util.List;
import java.util.UUID;

public record PlaceOrderResponse(
        UUID orderId,
        String orderNumber,
        String status,
        long totalAmount,
        List<OrderItemResponse> items
) {
    public record OrderItemResponse(
            UUID orderItemId,
            UUID variantId,
            String productName,
            String variantName,
            long unitPrice,
            int quantity,
            long subtotal
    ) {}
}
