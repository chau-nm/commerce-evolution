package dev.chaunm.commerceevolution.catalog.application.event.product;

import dev.chaunm.commerceevolution.catalog.domain.event.product.ProductArchivedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class ProductArchivedEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(ProductArchivedEvent event) {
        log.info("Handling product archived event for product: {}", event.productId());
    }
}
