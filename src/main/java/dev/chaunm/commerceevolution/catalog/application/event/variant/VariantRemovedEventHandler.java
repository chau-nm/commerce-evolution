package dev.chaunm.commerceevolution.catalog.application.event.variant;

import dev.chaunm.commerceevolution.catalog.domain.event.variant.VariantRemovedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class VariantRemovedEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(VariantRemovedEvent event) {
        log.info("Handling variant removed event for product: {}, variant: {}", event.productId(), event.variantId());
    }
}
