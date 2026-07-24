package dev.chaunm.commerceevolution.notification.infrastructure.persistence.repository;

import dev.chaunm.commerceevolution.notification.domain.model.Notification;
import dev.chaunm.commerceevolution.notification.domain.model.valueobject.NotificationId;
import dev.chaunm.commerceevolution.notification.domain.model.valueobject.RecipientId;
import dev.chaunm.commerceevolution.notification.domain.repository.NotificationRepository;
import dev.chaunm.commerceevolution.notification.infrastructure.persistence.entity.NotificationEntity;
import dev.chaunm.commerceevolution.notification.infrastructure.persistence.mapper.NotificationMapper;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationQuery;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

    private final JpaNotificationRepository jpaNotificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public Notification save(Notification notification) {
        return notificationMapper.toDomain(jpaNotificationRepository.save(notificationMapper.toEntity(notification)));
    }

    @Override
    public Optional<Notification> findById(NotificationId id) {
        return jpaNotificationRepository.findById(id.value()).map(notificationMapper::toDomain);
    }

    @Override
    public PaginationResult<Notification> findByRecipientId(RecipientId recipientId, PaginationQuery query) {
        Page<NotificationEntity> page = jpaNotificationRepository.findByRecipientId(recipientId.value(), query.toPageable());
        return PaginationResult.from(page).map(notificationMapper::toDomain);
    }
}
