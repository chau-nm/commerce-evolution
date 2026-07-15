package dev.chaunm.commerceevolution.catalog.application.event;

import dev.chaunm.commerceevolution.catalog.domain.event.ProductDuplicatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class ProductDuplicatedEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(ProductDuplicatedEvent event) {
        log.info("Handling product duplicated event for product: {} (source: {})",
                event.productId(), event.sourceProductId());
    }
}
