package dev.chaunm.commerceevolution.payment.application.usecase.getpayment;

import dev.chaunm.commerceevolution.payment.presentation.getpayment.GetPaymentResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface GetPaymentMapper {

    GetPaymentCommand toCommand(UUID orderId);

    GetPaymentResponse toResponse(GetPaymentResult result);
}
