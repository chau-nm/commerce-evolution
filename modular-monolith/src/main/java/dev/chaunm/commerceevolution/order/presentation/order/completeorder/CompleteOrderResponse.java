package dev.chaunm.commerceevolution.order.presentation.order.completeorder;

import java.util.UUID;

public record CompleteOrderResponse(
        UUID orderId,
        String status
) {}
