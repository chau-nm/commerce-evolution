package dev.chaunm.commerceevolution.notification.domain.factory;

import dev.chaunm.commerceevolution.notification.domain.event.NotificationCreatedEvent;
import dev.chaunm.commerceevolution.notification.domain.model.Notification;
import dev.chaunm.commerceevolution.notification.domain.model.valueobject.NotificationId;
import dev.chaunm.commerceevolution.notification.domain.model.valueobject.NotificationStatus;
import dev.chaunm.commerceevolution.notification.domain.model.valueobject.NotificationType;
import dev.chaunm.commerceevolution.notification.domain.model.valueobject.RecipientId;

import java.time.Instant;

public class NotificationFactory {

    public static Notification create(RecipientId recipientId, NotificationType type, String title, String content) {
        Notification notification = new Notification(
                NotificationId.generate(),
                recipientId,
                type,
                title,
                content,
                NotificationStatus.UNREAD,
                Instant.now(),
                null
        );

        notification.registerEvent(new NotificationCreatedEvent(
                notification.getId(),
                notification.getRecipientId(),
                notification.getType()
        ));

        return notification;
    }
}
