package dev.chaunm.commerceevolution.catalog.application.event.brand;

import dev.chaunm.commerceevolution.catalog.domain.event.brand.BrandCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class BrandCreatedEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(BrandCreatedEvent event) {
        log.info("Handling brand created event for brand: {}", event.brandId());
    }
}
