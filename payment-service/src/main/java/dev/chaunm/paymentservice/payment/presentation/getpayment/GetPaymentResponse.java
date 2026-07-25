package dev.chaunm.paymentservice.payment.presentation.getpayment;

import java.util.UUID;

public record GetPaymentResponse(
        UUID paymentId,
        UUID orderId,
        long amount,
        String status
) {}
