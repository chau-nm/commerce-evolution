package dev.chaunm.paymentservice.payment.application.usecase.getpayment;

import java.util.UUID;

public record GetPaymentResult(
        UUID paymentId,
        UUID orderId,
        long amount,
        String status
) {}
