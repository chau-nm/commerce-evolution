package dev.chaunm.commerceevolution.notification.presentation.notification.listnotifications;

import java.time.Instant;
import java.util.UUID;

public record NotificationSummaryResponse(
        UUID notificationId,
        String type,
        String title,
        String content,
        String status,
        Instant createdAt,
        Instant readAt
) {
}
