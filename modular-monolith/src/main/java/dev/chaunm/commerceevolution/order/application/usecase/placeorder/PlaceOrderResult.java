package dev.chaunm.commerceevolution.order.application.usecase.placeorder;

import java.util.List;
import java.util.UUID;

public record PlaceOrderResult(
        UUID orderId,
        String orderNumber,
        String status,
        long totalAmount,
        List<OrderItemResult> items
) {
    public record OrderItemResult(
            UUID orderItemId,
            UUID variantId,
            String productName,
            String variantName,
            long unitPrice,
            int quantity,
            long subtotal
    ) {}
}
