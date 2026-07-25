package dev.chaunm.paymentservice.payment.domain.event;

import dev.chaunm.paymentservice.payment.domain.model.valueobject.OrderId;
import dev.chaunm.paymentservice.payment.domain.model.valueobject.PaymentId;
import dev.chaunm.paymentservice.shared.domain.event.DomainEvent;

public record PaymentInitiatedEvent(
        PaymentId paymentId,
        OrderId orderId,
        long amount
) implements DomainEvent {
}
