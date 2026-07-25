package dev.chaunm.commerceevolution.paymentevents.presentation;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record PaymentEventWebhookRequest(
        @NotNull UUID paymentId,
        @NotNull UUID orderId,
        @NotNull @Pattern(regexp = "PAID|FAILED") String status
) {}
