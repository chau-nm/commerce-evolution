package dev.chaunm.commerceevolution.notification.application.usecase.marknotificationasread;

import dev.chaunm.commerceevolution.customer.application.port.CustomerDirectory;
import dev.chaunm.commerceevolution.notification.domain.exception.NotificationNotFoundException;
import dev.chaunm.commerceevolution.notification.domain.exception.RecipientNotFoundException;
import dev.chaunm.commerceevolution.notification.domain.model.Notification;
import dev.chaunm.commerceevolution.notification.domain.model.valueobject.NotificationId;
import dev.chaunm.commerceevolution.notification.domain.repository.NotificationRepository;
import dev.chaunm.commerceevolution.shared.application.currentuser.CurrentUserProvider;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MarkNotificationAsReadUseCaseImpl implements MarkNotificationAsReadUseCase {

    private final NotificationRepository notificationRepository;
    private final CustomerDirectory customerDirectory;
    private final CurrentUserProvider currentUserProvider;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public MarkNotificationAsReadResult markAsRead(MarkNotificationAsReadCommand command) {
        UUID customerId = customerDirectory.findCustomerIdByAccountId(currentUserProvider.getCurrentUser().accountId())
                .orElseThrow(RecipientNotFoundException::new);

        Notification notification = notificationRepository.findById(new NotificationId(command.notificationId()))
                .orElseThrow(NotificationNotFoundException::new);

        if (!notification.getRecipientId().value().equals(customerId)) {
            throw new NotificationNotFoundException();
        }

        notification.markAsRead();

        notificationRepository.save(notification);
        notification.domainEvents().forEach(domainEventPublisher::publish);

        return new MarkNotificationAsReadResult(
                notification.getId().value(),
                notification.getStatus().name(),
                notification.getReadAt()
        );
    }
}
