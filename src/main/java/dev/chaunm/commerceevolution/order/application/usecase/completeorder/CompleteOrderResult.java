package dev.chaunm.commerceevolution.order.application.usecase.completeorder;

import java.util.UUID;

public record CompleteOrderResult(
        UUID orderId,
        String status
) {}
