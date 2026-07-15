package dev.chaunm.commerceevolution.order.application.usecase.completeorder;

import dev.chaunm.commerceevolution.order.presentation.order.completeorder.CompleteOrderResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CompleteOrderMapper {

    CompleteOrderCommand toCommand(UUID orderId);

    CompleteOrderResponse toResponse(CompleteOrderResult result);
}
