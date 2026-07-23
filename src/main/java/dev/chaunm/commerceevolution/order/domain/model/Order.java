package dev.chaunm.commerceevolution.order.domain.model;

import dev.chaunm.commerceevolution.order.domain.event.OrderCancelledEvent;
import dev.chaunm.commerceevolution.order.domain.event.OrderCompletedEvent;
import dev.chaunm.commerceevolution.order.domain.event.OrderPaidEvent;
import dev.chaunm.commerceevolution.order.domain.event.OrderShippingStartedEvent;
import dev.chaunm.commerceevolution.order.domain.exception.InvalidOrderStatusTransitionException;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.CustomerId;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.Money;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.OrderId;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.OrderNumber;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.OrderStatus;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.ShippingAddress;
import dev.chaunm.commerceevolution.shared.domain.model.AggregateRoot;
import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order extends AggregateRoot {

    @Getter
    private final OrderId id;
    @Getter
    private final OrderNumber orderNumber;
    @Getter
    private final CustomerId customerId;
    @Getter
    private final ShippingAddress shippingAddress;
    @Getter
    private OrderStatus status;
    @Getter
    private final Money totalAmount;
    private final List<OrderItem> items;
    @Getter
    private final Instant createdAt;

    public Order(
            OrderId id,
            OrderNumber orderNumber,
            CustomerId customerId,
            ShippingAddress shippingAddress,
            OrderStatus status,
            Money totalAmount,
            List<OrderItem> items,
            Instant createdAt
    ) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.customerId = customerId;
        this.shippingAddress = shippingAddress;
        this.status = status;
        this.totalAmount = totalAmount;
        this.items = new ArrayList<>(items);
        this.createdAt = createdAt;
    }

    public void markPaid() {
        if (this.status != OrderStatus.PENDING) {
            throw new InvalidOrderStatusTransitionException(this.status, OrderStatus.PAID);
        }
        this.status = OrderStatus.PAID;
        registerEvent(new OrderPaidEvent(this.id));
    }

    public void startShipping() {
        if (this.status != OrderStatus.PAID) {
            throw new InvalidOrderStatusTransitionException(this.status, OrderStatus.SHIPPING);
        }
        this.status = OrderStatus.SHIPPING;
        registerEvent(new OrderShippingStartedEvent(this.id));
    }

    public void complete() {
        if (this.status != OrderStatus.SHIPPING) {
            throw new InvalidOrderStatusTransitionException(this.status, OrderStatus.COMPLETED);
        }
        this.status = OrderStatus.COMPLETED;
        registerEvent(new OrderCompletedEvent(this.id));
    }

    public void cancel() {
        if (this.status != OrderStatus.PENDING) {
            throw new InvalidOrderStatusTransitionException(this.status, OrderStatus.CANCELLED);
        }
        this.status = OrderStatus.CANCELLED;
        registerEvent(new OrderCancelledEvent(this.id));
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }
}
