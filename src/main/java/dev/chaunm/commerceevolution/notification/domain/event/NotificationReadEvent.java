package dev.chaunm.commerceevolution.notification.domain.event;

import dev.chaunm.commerceevolution.notification.domain.model.valueobject.NotificationId;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEvent;

public record NotificationReadEvent(
        NotificationId notificationId
) implements DomainEvent {
}
