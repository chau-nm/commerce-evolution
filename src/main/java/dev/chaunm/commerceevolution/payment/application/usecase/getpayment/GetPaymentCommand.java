package dev.chaunm.commerceevolution.payment.application.usecase.getpayment;

import java.util.UUID;

public record GetPaymentCommand(
        UUID orderId
) {}
