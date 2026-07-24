package dev.chaunm.commerceevolution.notification.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.ErrorCode;

public enum NotificationErrorCode implements ErrorCode {
    NOTIFICATION_NOT_FOUND,
    NOTIFICATION_ALREADY_READ,
    RECIPIENT_NOT_FOUND;

    @Override
    public String code() {
        return name();
    }
}
