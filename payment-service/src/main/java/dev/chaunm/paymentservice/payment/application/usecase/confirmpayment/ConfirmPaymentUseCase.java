package dev.chaunm.paymentservice.payment.application.usecase.confirmpayment;

public interface ConfirmPaymentUseCase {
    ConfirmPaymentResult confirm(ConfirmPaymentCommand command);
}
