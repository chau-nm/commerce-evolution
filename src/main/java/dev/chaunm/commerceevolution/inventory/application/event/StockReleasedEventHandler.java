package dev.chaunm.commerceevolution.inventory.application.event;

import dev.chaunm.commerceevolution.inventory.domain.event.StockReleasedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class StockReleasedEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(StockReleasedEvent event) {
        log.info("Handling stock released event for variant: {}, quantity: {}", event.variantId(), event.quantity());
    }
}
