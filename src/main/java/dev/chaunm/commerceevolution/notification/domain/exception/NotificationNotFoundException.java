package dev.chaunm.commerceevolution.notification.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.NotFoundException;

public class NotificationNotFoundException extends NotFoundException {
    public NotificationNotFoundException() {
        super(NotificationErrorCode.NOTIFICATION_NOT_FOUND, "Notification not found");
    }
}
