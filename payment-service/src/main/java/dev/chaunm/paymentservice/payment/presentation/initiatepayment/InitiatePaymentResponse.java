package dev.chaunm.paymentservice.payment.presentation.initiatepayment;

import java.util.UUID;

public record InitiatePaymentResponse(
        UUID paymentId,
        UUID orderId,
        long amount,
        String status
) {}
