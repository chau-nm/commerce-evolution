package dev.chaunm.commerceevolution.inventory.application.event;

import dev.chaunm.commerceevolution.inventory.domain.event.StockAdjustedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class StockAdjustedEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(StockAdjustedEvent event) {
        log.info("Handling stock adjusted event for variant: {}, delta: {}", event.variantId(), event.quantityDelta());
    }
}
