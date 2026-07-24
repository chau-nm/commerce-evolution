package dev.chaunm.commerceevolution.notification.application.usecase.listnotifications;

import dev.chaunm.commerceevolution.shared.application.pagination.PaginationResult;

public interface ListNotificationsUseCase {
    PaginationResult<NotificationSummaryItem> listNotifications(ListNotificationsCommand command);
}
