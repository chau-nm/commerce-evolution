package dev.chaunm.paymentservice.payment.application.usecase.initiatepayment;

import java.util.UUID;

public record InitiatePaymentCommand(
        UUID orderId,
        long amount
) {}
