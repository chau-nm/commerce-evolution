package dev.chaunm.commerceevolution.notification.application.usecase.marknotificationasread;

import java.util.UUID;

public record MarkNotificationAsReadCommand(
        UUID notificationId
) {
}
