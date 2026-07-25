package dev.chaunm.commerceevolution.order.application.usecase.cancelorder;

import java.util.UUID;

public record CancelOrderCommand(
        UUID orderId
) {}
