package dev.chaunm.commerceevolution.order.presentation.order.getorder;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record GetOrderResponse(
        UUID orderId,
        String orderNumber,
        UUID customerId,
        String status,
        long totalAmount,
        ShippingAddressResponse shippingAddress,
        List<OrderItemResponse> items,
        Instant createdAt
) {
    public record ShippingAddressResponse(
            String recipientName,
            String recipientPhone,
            String province,
            String district,
            String ward,
            String street,
            String postalCode
    ) {}

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
