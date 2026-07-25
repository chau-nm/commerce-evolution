package dev.chaunm.commerceevolution.order.presentation.order.getmyorders;

import java.time.Instant;
import java.util.UUID;

public record OrderSummaryResponse(
        UUID orderId,
        String orderNumber,
        String status,
        long totalAmount,
        Instant createdAt
) {}
