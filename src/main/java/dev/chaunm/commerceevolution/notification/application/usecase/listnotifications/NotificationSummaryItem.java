package dev.chaunm.commerceevolution.notification.application.usecase.listnotifications;

import java.time.Instant;
import java.util.UUID;

public record NotificationSummaryItem(
        UUID notificationId,
        String type,
        String title,
        String content,
        String status,
        Instant createdAt,
        Instant readAt
) {
}
