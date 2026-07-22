package dev.chaunm.commerceevolution.payment.application.usecase.confirmpayment;

import dev.chaunm.commerceevolution.payment.presentation.confirmpayment.ConfirmPaymentResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ConfirmPaymentMapper {

    ConfirmPaymentCommand toCommand(UUID paymentId);

    ConfirmPaymentResponse toResponse(ConfirmPaymentResult result);
}
