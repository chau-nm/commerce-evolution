package dev.chaunm.commerceevolution.notification.application.usecase.marknotificationasread;

public interface MarkNotificationAsReadUseCase {
    MarkNotificationAsReadResult markAsRead(MarkNotificationAsReadCommand command);
}
