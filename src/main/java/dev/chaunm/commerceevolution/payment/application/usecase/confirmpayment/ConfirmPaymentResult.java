package dev.chaunm.commerceevolution.payment.application.usecase.confirmpayment;

import java.util.UUID;

public record ConfirmPaymentResult(
        UUID paymentId,
        UUID orderId,
        String status
) {}
