package dev.chaunm.commerceevolution.payment.application.usecase.getpayment;

public interface GetPaymentUseCase {
    GetPaymentResult getByOrderId(GetPaymentCommand command);
}
