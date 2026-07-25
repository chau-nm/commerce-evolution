package dev.chaunm.commerceevolution.order.presentation.order.startshipping;

import java.util.UUID;

public record StartShippingResponse(
        UUID orderId,
        String status
) {}
