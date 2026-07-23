package dev.chaunm.commerceevolution.order.application.usecase.placeorder;

import dev.chaunm.commerceevolution.order.presentation.order.placeorder.PlaceOrderRequest;
import dev.chaunm.commerceevolution.order.presentation.order.placeorder.PlaceOrderResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlaceOrderMapper {

    PlaceOrderCommand toCommand(PlaceOrderRequest request);

    PlaceOrderResponse toResponse(PlaceOrderResult result);
}
