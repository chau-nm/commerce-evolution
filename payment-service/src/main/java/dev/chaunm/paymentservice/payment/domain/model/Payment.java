package dev.chaunm.paymentservice.payment.domain.model;

import dev.chaunm.paymentservice.payment.domain.event.PaymentFailedEvent;
import dev.chaunm.paymentservice.payment.domain.event.PaymentSucceededEvent;
import dev.chaunm.paymentservice.payment.domain.exception.InvalidPaymentStatusTransitionException;
import dev.chaunm.paymentservice.payment.domain.model.valueobject.Money;
import dev.chaunm.paymentservice.payment.domain.model.valueobject.OrderId;
import dev.chaunm.paymentservice.payment.domain.model.valueobject.PaymentId;
import dev.chaunm.paymentservice.payment.domain.model.valueobject.PaymentStatus;
import dev.chaunm.paymentservice.shared.domain.model.AggregateRoot;
import lombok.Getter;

@Getter
public class Payment extends AggregateRoot {

    private final PaymentId id;
    private final OrderId orderId;
    private final Money amount;
    private PaymentStatus status;

    public Payment(PaymentId id, OrderId orderId, Money amount, PaymentStatus status) {
        this.id = id;
        this.orderId = orderId;
        this.amount = amount;
        this.status = status;
    }

    public void markSucceeded() {
        if (this.status != PaymentStatus.PENDING) {
            throw new InvalidPaymentStatusTransitionException(this.status, PaymentStatus.PAID);
        }
        this.status = PaymentStatus.PAID;
        registerEvent(new PaymentSucceededEvent(this.id, this.orderId));
    }

    public void markFailed() {
        if (this.status != PaymentStatus.PENDING) {
            throw new InvalidPaymentStatusTransitionException(this.status, PaymentStatus.FAILED);
        }
        this.status = PaymentStatus.FAILED;
        registerEvent(new PaymentFailedEvent(this.id, this.orderId));
    }
}
