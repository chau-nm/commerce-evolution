package dev.chaunm.commerceevolution.inventory.application.event;

import dev.chaunm.commerceevolution.inventory.domain.event.InventoryCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class InventoryCreatedEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(InventoryCreatedEvent event) {
        log.info("Handling inventory created event for variant: {}", event.variantId());
    }
}
