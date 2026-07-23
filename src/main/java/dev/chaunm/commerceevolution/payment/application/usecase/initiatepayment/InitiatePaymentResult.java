package dev.chaunm.commerceevolution.payment.application.usecase.initiatepayment;

import java.util.UUID;

public record InitiatePaymentResult(
        UUID paymentId,
        UUID orderId,
        long amount,
        String status
) {}
