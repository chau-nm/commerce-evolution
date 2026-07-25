package dev.chaunm.commerceevolution.catalog.application.event.category;

import dev.chaunm.commerceevolution.catalog.domain.event.category.CategoryUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class CategoryUpdatedEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(CategoryUpdatedEvent event) {
        log.info("Handling category updated event for category: {}", event.categoryId());
    }
}
