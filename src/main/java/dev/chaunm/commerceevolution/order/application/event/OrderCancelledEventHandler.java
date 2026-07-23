package dev.chaunm.commerceevolution.order.application.event;

import dev.chaunm.commerceevolution.order.domain.event.OrderCancelledEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class OrderCancelledEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(OrderCancelledEvent event) {
        log.info("Handling order cancelled event for order: {}", event.orderId());
    }
}
