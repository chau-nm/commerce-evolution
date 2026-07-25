package dev.chaunm.commerceevolution.notification.presentation.notification.listnotifications;

import dev.chaunm.commerceevolution.shared.presentation.pagination.PaginationRequest;

public record ListNotificationsRequest(
        PaginationRequest pagination
) {
}
