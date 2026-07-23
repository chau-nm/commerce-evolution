package dev.chaunm.commerceevolution.payment.application.event;

import dev.chaunm.commerceevolution.payment.domain.event.PaymentSucceededEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class PaymentSucceededEventHandler {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(PaymentSucceededEvent event) {
        log.info("Handling payment succeeded event for payment: {}, order: {}", event.paymentId(), event.orderId());
    }
}
