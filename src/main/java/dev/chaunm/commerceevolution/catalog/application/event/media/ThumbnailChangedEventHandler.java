package dev.chaunm.commerceevolution.catalog.application.event.media;

import dev.chaunm.commerceevolution.catalog.domain.event.media.ThumbnailChangedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class ThumbnailChangedEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(ThumbnailChangedEvent event) {
        log.info("Handling thumbnail changed event for product: {}, media: {}", event.productId(), event.mediaId());
    }
}
