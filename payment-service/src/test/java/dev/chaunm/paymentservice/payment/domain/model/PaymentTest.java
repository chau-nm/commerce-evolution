package dev.chaunm.paymentservice.payment.domain.model;

import dev.chaunm.paymentservice.payment.domain.event.PaymentFailedEvent;
import dev.chaunm.paymentservice.payment.domain.event.PaymentInitiatedEvent;
import dev.chaunm.paymentservice.payment.domain.event.PaymentSucceededEvent;
import dev.chaunm.paymentservice.payment.domain.exception.InvalidMoneyException;
import dev.chaunm.paymentservice.payment.domain.exception.InvalidPaymentStatusTransitionException;
import dev.chaunm.paymentservice.payment.domain.factory.PaymentFactory;
import dev.chaunm.paymentservice.payment.domain.model.valueobject.Money;
import dev.chaunm.paymentservice.payment.domain.model.valueobject.OrderId;
import dev.chaunm.paymentservice.payment.domain.model.valueobject.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PaymentTest {

    private Payment payment;

    @BeforeEach
    void setUp() {
        payment = PaymentFactory.initiate(new OrderId(UUID.randomUUID()), new Money(100_000));
        payment.clearDomainEvents();
    }

    @Test
    void initiateStartsPendingAndRegistersEvent() {
        Payment fresh = PaymentFactory.initiate(new OrderId(UUID.randomUUID()), new Money(50_000));

        assertThat(fresh.getStatus()).isEqualTo(PaymentStatus.PENDING);
        assertThat(fresh.domainEvents()).singleElement().isInstanceOf(PaymentInitiatedEvent.class);
    }

    @Test
    void moneyRejectsZeroOrNegativeAmount() {
        assertThatThrownBy(() -> new Money(0)).isInstanceOf(InvalidMoneyException.class);
        assertThatThrownBy(() -> new Money(-1)).isInstanceOf(InvalidMoneyException.class);
    }

    @Test
    void markSucceededTransitionsToPaidAndRegistersEvent() {
        payment.markSucceeded();

        assertThat(payment.getStatus()).isEqualTo(PaymentStatus.PAID);
        assertThat(payment.domainEvents()).singleElement().isInstanceOf(PaymentSucceededEvent.class);
    }

    @Test
    void markFailedTransitionsToFailedAndRegistersEvent() {
        payment.markFailed();

        assertThat(payment.getStatus()).isEqualTo(PaymentStatus.FAILED);
        assertThat(payment.domainEvents()).singleElement().isInstanceOf(PaymentFailedEvent.class);
    }

    @Test
    void markSucceededRejectsWhenAlreadySettled() {
        payment.markSucceeded();

        assertThatThrownBy(payment::markSucceeded)
                .isInstanceOf(InvalidPaymentStatusTransitionException.class);
    }

    @Test
    void markFailedRejectsWhenAlreadySettled() {
        payment.markFailed();

        assertThatThrownBy(payment::markFailed)
                .isInstanceOf(InvalidPaymentStatusTransitionException.class);
    }
}
