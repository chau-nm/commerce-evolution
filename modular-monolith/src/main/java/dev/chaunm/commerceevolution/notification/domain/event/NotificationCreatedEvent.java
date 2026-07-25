package dev.chaunm.commerceevolution.notification.domain.event;

import dev.chaunm.commerceevolution.notification.domain.model.valueobject.NotificationId;
import dev.chaunm.commerceevolution.notification.domain.model.valueobject.NotificationType;
import dev.chaunm.commerceevolution.notification.domain.model.valueobject.RecipientId;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEvent;

public record NotificationCreatedEvent(
        NotificationId notificationId,
        RecipientId recipientId,
        NotificationType type
) implements DomainEvent {
}
