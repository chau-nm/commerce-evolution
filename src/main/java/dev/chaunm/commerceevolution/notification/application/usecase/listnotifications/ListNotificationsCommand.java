package dev.chaunm.commerceevolution.notification.application.usecase.listnotifications;

import dev.chaunm.commerceevolution.shared.presentation.pagination.PaginationRequest;

public record ListNotificationsCommand(
        PaginationRequest pagination
) {
}
