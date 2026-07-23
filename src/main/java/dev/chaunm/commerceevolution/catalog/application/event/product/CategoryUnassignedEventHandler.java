package dev.chaunm.commerceevolution.catalog.application.event.product;

import dev.chaunm.commerceevolution.catalog.domain.event.product.CategoryUnassignedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class CategoryUnassignedEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(CategoryUnassignedEvent event) {
        log.info("Handling category unassigned event for product: {}", event.productId());
    }
}
