package dev.chaunm.commerceevolution.order.application.usecase.getorder;

import java.util.UUID;

public record GetOrderCommand(
        UUID orderId
) {}
