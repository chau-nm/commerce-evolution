package dev.chaunm.paymentservice.payment.application.usecase.confirmpayment;

import java.util.UUID;

public record ConfirmPaymentCommand(
        UUID paymentId
) {}
