package dev.chaunm.commerceevolution.order.application.usecase.placeorder;

import dev.chaunm.commerceevolution.order.domain.model.Order;
import dev.chaunm.commerceevolution.order.presentation.order.placeorder.PlaceOrderRequest;
import dev.chaunm.commerceevolution.order.presentation.order.placeorder.PlaceOrderResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlaceOrderMapper {

    PlaceOrderCommand toCommand(PlaceOrderRequest request);

    default PlaceOrderResult toResult(Order order) {
        return new PlaceOrderResult(
                order.getId().value(),
                order.getOrderNumber().value(),
                order.getStatus().name(),
                order.getTotalAmount().amount(),
                order.getItems().stream()
                        .map(item -> new PlaceOrderResult.OrderItemResult(
                                item.getId().value(),
                                item.getVariantId().value(),
                                item.getProductName(),
                                item.getVariantName(),
                                item.getUnitPrice().amount(),
                                item.getQuantity().value(),
                                item.getSubtotal().amount()
                        ))
                        .toList()
        );
    }

    PlaceOrderResponse toResponse(PlaceOrderResult result);
}
