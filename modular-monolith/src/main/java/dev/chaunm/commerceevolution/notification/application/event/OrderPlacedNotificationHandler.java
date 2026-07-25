package dev.chaunm.commerceevolution.notification.application.event;

import dev.chaunm.commerceevolution.notification.application.usecase.createnotification.CreateNotificationCommand;
import dev.chaunm.commerceevolution.notification.application.usecase.createnotification.CreateNotificationUseCase;
import dev.chaunm.commerceevolution.notification.domain.model.valueobject.NotificationType;
import dev.chaunm.commerceevolution.order.domain.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * The only coupling point between notification and order: reacts to order's own published
 * domain event (never touches Order's aggregate/repository) to notify the customer that placed
 * it. Runs after the order-placing transaction has committed.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OrderPlacedNotificationHandler {

    private final CreateNotificationUseCase createNotificationUseCase;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(OrderCreatedEvent event) {
        log.info("Creating order placed notification for customer: {}", event.customerId());
        createNotificationUseCase.create(new CreateNotificationCommand(
                event.customerId().value(),
                NotificationType.ORDER_PLACED,
                "Order placed",
                "Your order " + event.orderNumber().value() + " has been placed successfully."
        ));
    }
}
