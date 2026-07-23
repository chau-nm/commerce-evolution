package dev.chaunm.commerceevolution.payment.application.usecase.confirmpayment;

import java.util.UUID;

public record ConfirmPaymentCommand(
        UUID paymentId
) {}
