package dev.chaunm.paymentservice.payment.application.usecase.getpayment;

import java.util.UUID;

public record GetPaymentCommand(
        UUID orderId
) {}
