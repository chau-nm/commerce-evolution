package dev.chaunm.commerceevolution.order.application.usecase.startshipping;

import java.util.UUID;

public record StartShippingResult(
        UUID orderId,
        String status
) {}
