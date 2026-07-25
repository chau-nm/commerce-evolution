package dev.chaunm.commerceevolution.notification.infrastructure.persistence.repository;

import dev.chaunm.commerceevolution.notification.infrastructure.persistence.entity.NotificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaNotificationRepository extends JpaRepository<NotificationEntity, UUID> {
    Page<NotificationEntity> findByRecipientId(UUID recipientId, Pageable pageable);
}
