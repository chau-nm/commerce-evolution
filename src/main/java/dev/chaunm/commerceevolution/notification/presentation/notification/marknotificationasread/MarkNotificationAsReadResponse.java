package dev.chaunm.commerceevolution.notification.presentation.notification.marknotificationasread;

import java.time.Instant;
import java.util.UUID;

public record MarkNotificationAsReadResponse(
        UUID notificationId,
        String status,
        Instant readAt
) {
}
