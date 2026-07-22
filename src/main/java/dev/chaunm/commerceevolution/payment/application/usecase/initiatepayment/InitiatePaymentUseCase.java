package dev.chaunm.commerceevolution.payment.application.usecase.initiatepayment;

public interface InitiatePaymentUseCase {
    InitiatePaymentResult initiate(InitiatePaymentCommand command);
}
