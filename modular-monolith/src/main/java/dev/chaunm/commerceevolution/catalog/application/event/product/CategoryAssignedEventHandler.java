package dev.chaunm.commerceevolution.catalog.application.event.product;

import dev.chaunm.commerceevolution.catalog.domain.event.product.CategoryAssignedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class CategoryAssignedEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(CategoryAssignedEvent event) {
        log.info("Handling category assigned event for product: {}, category: {}", event.productId(), event.categoryId());
    }
}
