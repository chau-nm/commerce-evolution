package dev.chaunm.commerceevolution.notification.application.usecase.marknotificationasread;

import dev.chaunm.commerceevolution.notification.presentation.notification.marknotificationasread.MarkNotificationAsReadResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface MarkNotificationAsReadMapper {

    MarkNotificationAsReadCommand toCommand(UUID notificationId);

    MarkNotificationAsReadResponse toResponse(MarkNotificationAsReadResult result);
}
