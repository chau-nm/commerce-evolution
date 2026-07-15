package dev.chaunm.commerceevolution.catalog.application.event.media;

import dev.chaunm.commerceevolution.catalog.domain.event.media.MediaUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class MediaUpdatedEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(MediaUpdatedEvent event) {
        log.info("Handling media updated event for product: {}, media: {}", event.productId(), event.mediaId());
    }
}
