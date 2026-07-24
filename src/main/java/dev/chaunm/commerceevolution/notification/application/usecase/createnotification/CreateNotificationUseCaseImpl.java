package dev.chaunm.commerceevolution.notification.application.usecase.createnotification;

import dev.chaunm.commerceevolution.notification.domain.factory.NotificationFactory;
import dev.chaunm.commerceevolution.notification.domain.model.Notification;
import dev.chaunm.commerceevolution.notification.domain.model.valueobject.RecipientId;
import dev.chaunm.commerceevolution.notification.domain.repository.NotificationRepository;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateNotificationUseCaseImpl implements CreateNotificationUseCase {

    private final NotificationRepository notificationRepository;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public CreateNotificationResult create(CreateNotificationCommand command) {
        Notification notification = NotificationFactory.create(
                new RecipientId(command.recipientId()),
                command.type(),
                command.title(),
                command.content()
        );

        notificationRepository.save(notification);
        notification.domainEvents().forEach(domainEventPublisher::publish);

        return new CreateNotificationResult(notification.getId().value());
    }
}
