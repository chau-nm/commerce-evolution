package dev.chaunm.commerceevolution.catalog.application.event;

import dev.chaunm.commerceevolution.catalog.domain.event.VariantUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class VariantUpdatedEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(VariantUpdatedEvent event) {
        log.info("Handling variant updated event for product: {}, variant: {}", event.productId(), event.variantId());
    }
}
