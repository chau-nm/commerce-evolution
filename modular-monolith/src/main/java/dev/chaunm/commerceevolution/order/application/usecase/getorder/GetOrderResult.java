package dev.chaunm.commerceevolution.order.application.usecase.getorder;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record GetOrderResult(
        UUID orderId,
        String orderNumber,
        UUID customerId,
        String status,
        long totalAmount,
        ShippingAddressResult shippingAddress,
        List<OrderItemResult> items,
        Instant createdAt
) {
    public record ShippingAddressResult(
            String recipientName,
            String recipientPhone,
            String province,
            String district,
            String ward,
            String street,
            String postalCode
    ) {}

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
