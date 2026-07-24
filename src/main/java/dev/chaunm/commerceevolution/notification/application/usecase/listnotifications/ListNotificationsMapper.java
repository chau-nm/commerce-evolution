package dev.chaunm.commerceevolution.notification.application.usecase.listnotifications;

import dev.chaunm.commerceevolution.notification.presentation.notification.listnotifications.ListNotificationsRequest;
import dev.chaunm.commerceevolution.notification.presentation.notification.listnotifications.NotificationSummaryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ListNotificationsMapper {

    ListNotificationsCommand toCommand(ListNotificationsRequest request);

    NotificationSummaryResponse toResponse(NotificationSummaryItem item);
}
