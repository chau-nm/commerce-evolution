package dev.chaunm.commerceevolution.notification.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.NotFoundException;

public class RecipientNotFoundException extends NotFoundException {
    public RecipientNotFoundException() {
        super(NotificationErrorCode.RECIPIENT_NOT_FOUND, "No customer profile found for the current account");
    }
}
