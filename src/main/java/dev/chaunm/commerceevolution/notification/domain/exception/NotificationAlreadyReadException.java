package dev.chaunm.commerceevolution.notification.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class NotificationAlreadyReadException extends DomainException {
    public NotificationAlreadyReadException() {
        super(NotificationErrorCode.NOTIFICATION_ALREADY_READ, "Notification has already been read");
    }
}
