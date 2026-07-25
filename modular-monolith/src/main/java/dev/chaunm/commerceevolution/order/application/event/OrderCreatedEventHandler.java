package dev.chaunm.commerceevolution.order.application.event;

import dev.chaunm.commerceevolution.order.domain.event.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class OrderCreatedEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(OrderCreatedEvent event) {
        log.info("Handling order created event for order: {}, customer: {}", event.orderId(), event.customerId());
    }
}
