package dev.chaunm.commerceevolution.order.application.usecase.cancelorder;

import dev.chaunm.commerceevolution.order.presentation.order.cancelorder.CancelOrderResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CancelOrderMapper {

    CancelOrderCommand toCommand(UUID orderId);

    CancelOrderResponse toResponse(CancelOrderResult result);
}
