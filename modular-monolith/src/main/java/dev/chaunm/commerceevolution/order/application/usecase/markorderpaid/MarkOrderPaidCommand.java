package dev.chaunm.commerceevolution.order.application.usecase.markorderpaid;

import java.util.UUID;

public record MarkOrderPaidCommand(
        UUID orderId
) {}
