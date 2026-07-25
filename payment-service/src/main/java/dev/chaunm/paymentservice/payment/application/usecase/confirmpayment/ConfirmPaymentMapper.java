package dev.chaunm.paymentservice.payment.application.usecase.confirmpayment;

import dev.chaunm.paymentservice.payment.presentation.confirmpayment.ConfirmPaymentResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ConfirmPaymentMapper {

    ConfirmPaymentCommand toCommand(UUID paymentId);

    ConfirmPaymentResponse toResponse(ConfirmPaymentResult result);
}
