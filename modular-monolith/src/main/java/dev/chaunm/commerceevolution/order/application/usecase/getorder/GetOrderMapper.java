package dev.chaunm.commerceevolution.order.application.usecase.getorder;

import dev.chaunm.commerceevolution.order.domain.model.Order;
import dev.chaunm.commerceevolution.order.domain.model.OrderItem;
import dev.chaunm.commerceevolution.order.presentation.order.getorder.GetOrderResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface GetOrderMapper {

    GetOrderCommand toCommand(UUID orderId);

    default GetOrderResult toResult(Order order) {
        return new GetOrderResult(
                order.getId().value(),
                order.getOrderNumber().value(),
                order.getCustomerId().value(),
                order.getStatus().name(),
                order.getTotalAmount().amount(),
                new GetOrderResult.ShippingAddressResult(
                        order.getShippingAddress().recipientName(),
                        order.getShippingAddress().recipientPhone(),
                        order.getShippingAddress().province(),
                        order.getShippingAddress().district(),
                        order.getShippingAddress().ward(),
                        order.getShippingAddress().street(),
                        order.getShippingAddress().postalCode()
                ),
                order.getItems().stream().map(this::toItemResult).toList(),
                order.getCreatedAt()
        );
    }

    default GetOrderResult.OrderItemResult toItemResult(OrderItem item) {
        return new GetOrderResult.OrderItemResult(
                item.getId().value(),
                item.getVariantId().value(),
                item.getProductName(),
                item.getVariantName(),
                item.getUnitPrice().amount(),
                item.getQuantity().value(),
                item.getSubtotal().amount()
        );
    }

    GetOrderResponse toResponse(GetOrderResult result);
}
