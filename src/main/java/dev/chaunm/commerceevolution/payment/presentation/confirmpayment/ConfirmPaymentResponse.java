package dev.chaunm.commerceevolution.payment.presentation.confirmpayment;

import java.util.UUID;

public record ConfirmPaymentResponse(
        UUID paymentId,
        UUID orderId,
        String status
) {}
