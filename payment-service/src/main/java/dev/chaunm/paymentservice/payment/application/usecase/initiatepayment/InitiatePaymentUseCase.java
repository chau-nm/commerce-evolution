package dev.chaunm.paymentservice.payment.application.usecase.initiatepayment;

public interface InitiatePaymentUseCase {
    InitiatePaymentResult initiate(InitiatePaymentCommand command);
}
