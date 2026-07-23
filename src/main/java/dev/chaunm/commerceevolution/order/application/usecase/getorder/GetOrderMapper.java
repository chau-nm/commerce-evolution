package dev.chaunm.commerceevolution.order.application.usecase.getorder;

import dev.chaunm.commerceevolution.order.presentation.order.getorder.GetOrderResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface GetOrderMapper {

    GetOrderCommand toCommand(UUID orderId);

    GetOrderResponse toResponse(GetOrderResult result);
}
