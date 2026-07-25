package dev.chaunm.commerceevolution.catalog.application.event.category;

import dev.chaunm.commerceevolution.catalog.domain.event.category.CategoryDeletedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class CategoryDeletedEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(CategoryDeletedEvent event) {
        log.info("Handling category deleted event for category: {}", event.categoryId());
    }
}
