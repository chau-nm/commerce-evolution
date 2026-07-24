package dev.chaunm.commerceevolution.notification.application.event;

import dev.chaunm.commerceevolution.notification.application.usecase.createnotification.CreateNotificationCommand;
import dev.chaunm.commerceevolution.notification.application.usecase.createnotification.CreateNotificationUseCase;
import dev.chaunm.commerceevolution.notification.domain.model.valueobject.NotificationType;
import dev.chaunm.commerceevolution.order.application.port.OrderDirectory;
import dev.chaunm.commerceevolution.payment.domain.event.PaymentFailedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Reacts to payment's own published domain event (never touches Payment's aggregate/repository)
 * to notify the customer that their payment failed. Payment events only carry an orderId, so
 * the recipient is resolved through {@link OrderDirectory} — order's public cross-context port —
 * rather than reaching into the Order aggregate directly.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentFailedNotificationHandler {

    private final CreateNotificationUseCase createNotificationUseCase;
    private final OrderDirectory orderDirectory;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(PaymentFailedEvent event) {
        orderDirectory.findCustomerIdByOrderId(event.orderId().value()).ifPresentOrElse(
                customerId -> createNotificationUseCase.create(new CreateNotificationCommand(
                        customerId,
                        NotificationType.PAYMENT_FAILED,
                        "Payment failed",
                        "Your payment for order " + event.orderId() + " could not be processed."
                )),
                () -> log.warn("Could not resolve customer for order: {}, skipping payment failed notification", event.orderId())
        );
    }
}
