package dev.chaunm.paymentservice.payment.application.event;

import dev.chaunm.paymentservice.payment.application.port.PaymentEventNotifier;
import dev.chaunm.paymentservice.payment.domain.event.PaymentFailedEvent;
import dev.chaunm.paymentservice.payment.domain.model.valueobject.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentFailedEventHandler {

    private final PaymentEventNotifier paymentEventNotifier;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(PaymentFailedEvent event) {
        log.info("Handling payment failed event for payment: {}, order: {}", event.paymentId(), event.orderId());
        paymentEventNotifier.notifyOutcome(event.paymentId(), event.orderId(), PaymentStatus.FAILED);
    }
}
