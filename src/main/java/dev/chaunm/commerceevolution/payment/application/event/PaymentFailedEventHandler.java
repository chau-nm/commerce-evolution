package dev.chaunm.commerceevolution.payment.application.event;

import dev.chaunm.commerceevolution.payment.domain.event.PaymentFailedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class PaymentFailedEventHandler {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(PaymentFailedEvent event) {
        log.info("Handling payment failed event for payment: {}, order: {}", event.paymentId(), event.orderId());
    }
}
