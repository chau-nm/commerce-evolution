package dev.chaunm.commerceevolution.order.domain.event;

import dev.chaunm.commerceevolution.order.domain.model.valueobject.CustomerId;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.OrderId;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.OrderNumber;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEvent;

public record OrderCreatedEvent(
        OrderId orderId,
        CustomerId customerId,
        OrderNumber orderNumber,
        long totalAmount
) implements DomainEvent {
}
