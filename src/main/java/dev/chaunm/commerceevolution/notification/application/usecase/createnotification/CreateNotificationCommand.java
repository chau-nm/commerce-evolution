package dev.chaunm.commerceevolution.notification.application.usecase.createnotification;

import dev.chaunm.commerceevolution.notification.domain.model.valueobject.NotificationType;

import java.util.UUID;

public record CreateNotificationCommand(
        UUID recipientId,
        NotificationType type,
        String title,
        String content
) {
}
