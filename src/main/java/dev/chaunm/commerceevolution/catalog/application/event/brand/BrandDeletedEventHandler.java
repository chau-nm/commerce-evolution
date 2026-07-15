package dev.chaunm.commerceevolution.catalog.application.event.brand;

import dev.chaunm.commerceevolution.catalog.domain.event.brand.BrandDeletedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class BrandDeletedEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(BrandDeletedEvent event) {
        log.info("Handling brand deleted event for brand: {}", event.brandId());
    }
}
