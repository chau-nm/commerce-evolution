package dev.chaunm.commerceevolution.paymentevents.application.usecase.handlepaymentevent;

import java.util.UUID;

public record HandlePaymentEventCommand(
        UUID paymentId,
        UUID orderId,
        String status
) {}
