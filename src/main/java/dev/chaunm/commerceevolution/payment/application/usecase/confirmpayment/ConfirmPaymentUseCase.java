package dev.chaunm.commerceevolution.payment.application.usecase.confirmpayment;

public interface ConfirmPaymentUseCase {
    ConfirmPaymentResult confirm(ConfirmPaymentCommand command);
}
