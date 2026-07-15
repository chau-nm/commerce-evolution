package dev.chaunm.commerceevolution.order.domain.event;

import dev.chaunm.commerceevolution.order.domain.model.valueobject.OrderId;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEvent;

public record OrderCancelledEvent(
        OrderId orderId
) implements DomainEvent {
}
