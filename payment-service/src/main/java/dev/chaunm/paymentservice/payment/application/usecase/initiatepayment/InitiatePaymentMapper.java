package dev.chaunm.paymentservice.payment.application.usecase.initiatepayment;

import dev.chaunm.paymentservice.payment.presentation.initiatepayment.InitiatePaymentRequest;
import dev.chaunm.paymentservice.payment.presentation.initiatepayment.InitiatePaymentResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InitiatePaymentMapper {

    InitiatePaymentCommand toCommand(InitiatePaymentRequest request);

    InitiatePaymentResponse toResponse(InitiatePaymentResult result);
}
