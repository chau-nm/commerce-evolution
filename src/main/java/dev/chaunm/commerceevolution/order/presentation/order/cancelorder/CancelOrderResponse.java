package dev.chaunm.commerceevolution.order.presentation.order.cancelorder;

import java.util.UUID;

public record CancelOrderResponse(
        UUID orderId,
        String status
) {}
