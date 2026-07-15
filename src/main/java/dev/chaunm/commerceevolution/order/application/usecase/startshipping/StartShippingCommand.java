package dev.chaunm.commerceevolution.order.application.usecase.startshipping;

import java.util.UUID;

public record StartShippingCommand(
        UUID orderId
) {}
