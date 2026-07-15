package dev.chaunm.commerceevolution.catalog.application.event;

import dev.chaunm.commerceevolution.catalog.domain.event.ProductDeletedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class ProductDeletedEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(ProductDeletedEvent event) {
        log.info("Handling product deleted event for product: {}", event.productId());
    }
}
