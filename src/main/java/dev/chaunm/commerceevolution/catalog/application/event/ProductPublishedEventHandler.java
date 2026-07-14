package dev.chaunm.commerceevolution.catalog.application.event;

import dev.chaunm.commerceevolution.catalog.domain.event.ProductPublishedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class ProductPublishedEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(ProductPublishedEvent event) {
        log.info("Handling product published event for product: {}", event.productId());
    }
}
