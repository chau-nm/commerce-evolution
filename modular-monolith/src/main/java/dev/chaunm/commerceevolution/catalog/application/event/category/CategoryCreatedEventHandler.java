package dev.chaunm.commerceevolution.catalog.application.event.category;

import dev.chaunm.commerceevolution.catalog.domain.event.category.CategoryCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class CategoryCreatedEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(CategoryCreatedEvent event) {
        log.info("Handling category created event for category: {}", event.categoryId());
    }
}
