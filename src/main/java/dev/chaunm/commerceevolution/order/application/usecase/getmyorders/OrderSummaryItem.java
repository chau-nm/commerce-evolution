package dev.chaunm.commerceevolution.order.application.usecase.getmyorders;

import java.time.Instant;
import java.util.UUID;

public record OrderSummaryItem(
        UUID orderId,
        String orderNumber,
        String status,
        long totalAmount,
        Instant createdAt
) {}
