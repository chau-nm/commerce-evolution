package dev.chaunm.paymentservice.payment.application.usecase.getpayment;

public interface GetPaymentUseCase {
    GetPaymentResult getByOrderId(GetPaymentCommand command);
}
