package dev.chaunm.paymentservice.payment.presentation.initiatepayment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record InitiatePaymentRequest(
        @NotNull UUID orderId,
        @Positive long amount
) {}
