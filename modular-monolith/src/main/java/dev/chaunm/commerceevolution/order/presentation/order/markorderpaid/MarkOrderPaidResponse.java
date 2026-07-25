package dev.chaunm.commerceevolution.order.presentation.order.markorderpaid;

import java.util.UUID;

public record MarkOrderPaidResponse(
        UUID orderId,
        String status
) {}
