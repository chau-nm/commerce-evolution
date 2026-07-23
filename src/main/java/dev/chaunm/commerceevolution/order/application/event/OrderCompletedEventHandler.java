package dev.chaunm.commerceevolution.order.application.event;

import dev.chaunm.commerceevolution.order.domain.event.OrderCompletedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class OrderCompletedEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(OrderCompletedEvent event) {
        log.info("Handling order completed event for order: {}", event.orderId());
    }
}
