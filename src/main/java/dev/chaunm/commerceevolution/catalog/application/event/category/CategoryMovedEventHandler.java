package dev.chaunm.commerceevolution.catalog.application.event.category;

import dev.chaunm.commerceevolution.catalog.domain.event.category.CategoryMovedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class CategoryMovedEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(CategoryMovedEvent event) {
        log.info("Handling category moved event for category: {}, newParent: {}", event.categoryId(), event.newParentId());
    }
}
