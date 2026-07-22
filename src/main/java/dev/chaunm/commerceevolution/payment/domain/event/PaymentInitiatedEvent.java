package dev.chaunm.commerceevolution.payment.domain.event;

import dev.chaunm.commerceevolution.payment.domain.model.valueobject.OrderId;
import dev.chaunm.commerceevolution.payment.domain.model.valueobject.PaymentId;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEvent;

public record PaymentInitiatedEvent(
        PaymentId paymentId,
        OrderId orderId,
        long amount
) implements DomainEvent {
}
