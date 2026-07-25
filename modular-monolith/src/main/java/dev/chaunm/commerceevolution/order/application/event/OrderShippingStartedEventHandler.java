package dev.chaunm.commerceevolution.order.application.event;

import dev.chaunm.commerceevolution.order.domain.event.OrderShippingStartedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class OrderShippingStartedEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(OrderShippingStartedEvent event) {
        log.info("Handling order shipping started event for order: {}", event.orderId());
    }
}
