package dev.chaunm.commerceevolution.notification.application.event;

import dev.chaunm.commerceevolution.notification.domain.event.NotificationCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class NotificationCreatedEventHandler {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(NotificationCreatedEvent event) {
        log.info("Handling notification created event for notification: {}, recipient: {}", event.notificationId(), event.recipientId());
    }
}
