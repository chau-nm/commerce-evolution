package dev.chaunm.commerceevolution.order.application.usecase.completeorder;

import java.util.UUID;

public record CompleteOrderCommand(
        UUID orderId
) {}
