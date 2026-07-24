package dev.chaunm.commerceevolution.notification.infrastructure.persistence.mapper;

import dev.chaunm.commerceevolution.notification.domain.model.Notification;
import dev.chaunm.commerceevolution.notification.domain.model.valueobject.NotificationId;
import dev.chaunm.commerceevolution.notification.domain.model.valueobject.RecipientId;
import dev.chaunm.commerceevolution.notification.infrastructure.persistence.entity.NotificationEntity;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    Notification toDomain(NotificationEntity entity);

    NotificationEntity toEntity(Notification domain);

    default UUID toUuid(NotificationId id) {
        return id == null ? null : id.value();
    }

    default NotificationId toNotificationId(UUID value) {
        return value == null ? null : new NotificationId(value);
    }

    default UUID toUuid(RecipientId id) {
        return id == null ? null : id.value();
    }

    default RecipientId toRecipientId(UUID value) {
        return value == null ? null : new RecipientId(value);
    }
}
