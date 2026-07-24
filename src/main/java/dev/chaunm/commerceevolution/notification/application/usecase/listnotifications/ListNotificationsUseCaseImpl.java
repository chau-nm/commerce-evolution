package dev.chaunm.commerceevolution.notification.application.usecase.listnotifications;

import dev.chaunm.commerceevolution.customer.application.port.CustomerDirectory;
import dev.chaunm.commerceevolution.notification.domain.exception.RecipientNotFoundException;
import dev.chaunm.commerceevolution.notification.domain.model.valueobject.RecipientId;
import dev.chaunm.commerceevolution.notification.domain.repository.NotificationRepository;
import dev.chaunm.commerceevolution.shared.application.currentuser.CurrentUserProvider;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationQuery;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ListNotificationsUseCaseImpl implements ListNotificationsUseCase {

    private final NotificationRepository notificationRepository;
    private final CustomerDirectory customerDirectory;
    private final CurrentUserProvider currentUserProvider;

    @Override
    @Transactional(readOnly = true)
    public PaginationResult<NotificationSummaryItem> listNotifications(ListNotificationsCommand command) {
        UUID customerId = customerDirectory.findCustomerIdByAccountId(currentUserProvider.getCurrentUser().accountId())
                .orElseThrow(RecipientNotFoundException::new);

        PaginationQuery query = PaginationQuery.from(command.pagination());

        return notificationRepository.findByRecipientId(new RecipientId(customerId), query)
                .map(notification -> new NotificationSummaryItem(
                        notification.getId().value(),
                        notification.getType().name(),
                        notification.getTitle(),
                        notification.getContent(),
                        notification.getStatus().name(),
                        notification.getCreatedAt(),
                        notification.getReadAt()
                ));
    }
}
