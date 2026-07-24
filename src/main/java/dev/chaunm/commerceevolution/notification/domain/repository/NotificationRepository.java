package dev.chaunm.commerceevolution.notification.domain.repository;

import dev.chaunm.commerceevolution.notification.domain.model.Notification;
import dev.chaunm.commerceevolution.notification.domain.model.valueobject.NotificationId;
import dev.chaunm.commerceevolution.notification.domain.model.valueobject.RecipientId;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationQuery;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationResult;

import java.util.Optional;

public interface NotificationRepository {
    Notification save(Notification notification);

    Optional<Notification> findById(NotificationId id);

    PaginationResult<Notification> findByRecipientId(RecipientId recipientId, PaginationQuery query);
}
