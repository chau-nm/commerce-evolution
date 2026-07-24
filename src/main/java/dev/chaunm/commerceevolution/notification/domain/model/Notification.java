package dev.chaunm.commerceevolution.notification.domain.model;

import dev.chaunm.commerceevolution.notification.domain.event.NotificationReadEvent;
import dev.chaunm.commerceevolution.notification.domain.exception.NotificationAlreadyReadException;
import dev.chaunm.commerceevolution.notification.domain.model.valueobject.NotificationId;
import dev.chaunm.commerceevolution.notification.domain.model.valueobject.NotificationStatus;
import dev.chaunm.commerceevolution.notification.domain.model.valueobject.NotificationType;
import dev.chaunm.commerceevolution.notification.domain.model.valueobject.RecipientId;
import dev.chaunm.commerceevolution.shared.domain.model.AggregateRoot;
import lombok.Getter;

import java.time.Instant;

public class Notification extends AggregateRoot {

    @Getter
    private final NotificationId id;
    @Getter
    private final RecipientId recipientId;
    @Getter
    private final NotificationType type;
    @Getter
    private final String title;
    @Getter
    private final String content;
    @Getter
    private NotificationStatus status;
    @Getter
    private final Instant createdAt;
    @Getter
    private Instant readAt;

    public Notification(
            NotificationId id,
            RecipientId recipientId,
            NotificationType type,
            String title,
            String content,
            NotificationStatus status,
            Instant createdAt,
            Instant readAt
    ) {
        this.id = id;
        this.recipientId = recipientId;
        this.type = type;
        this.title = title;
        this.content = content;
        this.status = status;
        this.createdAt = createdAt;
        this.readAt = readAt;
    }

    public void markAsRead() {
        if (this.status == NotificationStatus.READ) {
            throw new NotificationAlreadyReadException();
        }
        this.status = NotificationStatus.READ;
        this.readAt = Instant.now();
        registerEvent(new NotificationReadEvent(this.id));
    }
}
