package dev.chaunm.commerceevolution.order.domain.factory;

import dev.chaunm.commerceevolution.order.domain.event.OrderCreatedEvent;
import dev.chaunm.commerceevolution.order.domain.exception.EmptyOrderException;
import dev.chaunm.commerceevolution.order.domain.model.Order;
import dev.chaunm.commerceevolution.order.domain.model.OrderItem;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.CustomerId;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.Money;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.OrderId;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.OrderNumber;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.OrderStatus;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.ShippingAddress;

import java.time.Instant;
import java.util.List;

public class OrderFactory {

    public static Order create(CustomerId customerId, ShippingAddress shippingAddress, List<OrderItem> items) {
        if (items.isEmpty()) {
            throw new EmptyOrderException();
        }

        Money totalAmount = items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(Money::add)
                .orElseThrow(EmptyOrderException::new);

        Order order = new Order(
                OrderId.generate(),
                OrderNumber.generate(),
                customerId,
                shippingAddress,
                OrderStatus.PENDING,
                totalAmount,
                items,
                Instant.now()
        );

        order.registerEvent(new OrderCreatedEvent(
                order.getId(),
                order.getCustomerId(),
                order.getOrderNumber(),
                order.getTotalAmount().amount()
        ));

        return order;
    }
}
