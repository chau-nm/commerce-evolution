package dev.chaunm.commerceevolution.notification.application.usecase.marknotificationasread;

import java.time.Instant;
import java.util.UUID;

public record MarkNotificationAsReadResult(
        UUID notificationId,
        String status,
        Instant readAt
) {
}
