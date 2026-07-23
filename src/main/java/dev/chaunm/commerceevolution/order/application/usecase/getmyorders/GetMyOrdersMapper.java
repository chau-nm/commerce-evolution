package dev.chaunm.commerceevolution.order.application.usecase.getmyorders;

import dev.chaunm.commerceevolution.order.presentation.order.getmyorders.GetMyOrdersRequest;
import dev.chaunm.commerceevolution.order.presentation.order.getmyorders.OrderSummaryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetMyOrdersMapper {
    GetMyOrdersCommand toCommand(GetMyOrdersRequest request);
    OrderSummaryResponse toResponse(OrderSummaryItem item);
}
