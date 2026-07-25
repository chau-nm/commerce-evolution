package dev.chaunm.commerceevolution.notification.application.event;

import dev.chaunm.commerceevolution.notification.domain.event.NotificationReadEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class NotificationReadEventHandler {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(NotificationReadEvent event) {
        log.info("Handling notification read event for notification: {}", event.notificationId());
    }
}
