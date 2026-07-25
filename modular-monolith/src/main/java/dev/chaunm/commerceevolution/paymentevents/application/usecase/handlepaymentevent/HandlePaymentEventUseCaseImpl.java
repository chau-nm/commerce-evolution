package dev.chaunm.commerceevolution.paymentevents.application.usecase.handlepaymentevent;

import dev.chaunm.commerceevolution.notification.application.usecase.createnotification.CreateNotificationCommand;
import dev.chaunm.commerceevolution.notification.application.usecase.createnotification.CreateNotificationUseCase;
import dev.chaunm.commerceevolution.notification.domain.model.valueobject.NotificationType;
import dev.chaunm.commerceevolution.order.application.port.OrderDirectory;
import dev.chaunm.commerceevolution.order.application.usecase.markorderpaid.MarkOrderPaidCommand;
import dev.chaunm.commerceevolution.order.application.usecase.markorderpaid.MarkOrderPaidUseCase;
import dev.chaunm.commerceevolution.paymentevents.infrastructure.persistence.entity.ProcessedPaymentEventEntity;
import dev.chaunm.commerceevolution.paymentevents.infrastructure.persistence.repository.ProcessedPaymentEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Receiving end of payment-service's outbound webhook — the single inbound integration point
 * that replaces what used to be two separate in-process listeners (order's direct
 * MarkOrderPaidUseCase call from inside payment's ConfirmPaymentUseCaseImpl, and
 * notification's PaymentSucceededEvent/PaymentFailedEvent listeners) back when payment lived in
 * this JVM. Both reactions now happen from here instead, since payment-service can no longer
 * reach into order's or notification's use cases directly.
 * <p>
 * Idempotent by construction: payment-service retries this call under transient failures, and
 * a payment only ever settles once, so {@code paymentId} is recorded the first time it's seen
 * and every redelivery after that is a no-op.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HandlePaymentEventUseCaseImpl implements HandlePaymentEventUseCase {

    private static final String PAID = "PAID";

    private final ProcessedPaymentEventRepository processedPaymentEventRepository;
    private final MarkOrderPaidUseCase markOrderPaidUseCase;
    private final CreateNotificationUseCase createNotificationUseCase;
    private final OrderDirectory orderDirectory;

    @Override
    @Transactional
    public void handle(HandlePaymentEventCommand command) {
        if (processedPaymentEventRepository.existsById(command.paymentId())) {
            log.info("Payment event {} already processed, skipping duplicate delivery", command.paymentId());
            return;
        }
        processedPaymentEventRepository.save(new ProcessedPaymentEventEntity(
                command.paymentId(), command.orderId(), command.status()));

        if (PAID.equals(command.status())) {
            markOrderPaidUseCase.markPaid(new MarkOrderPaidCommand(command.orderId()));
            notify(command.orderId(), NotificationType.PAYMENT_SUCCEEDED, "Payment succeeded",
                    "Your payment for order " + command.orderId() + " was successful.");
        } else {
            notify(command.orderId(), NotificationType.PAYMENT_FAILED, "Payment failed",
                    "Your payment for order " + command.orderId() + " could not be processed.");
        }
    }

    private void notify(java.util.UUID orderId, NotificationType type, String title, String content) {
        orderDirectory.findCustomerIdByOrderId(orderId).ifPresentOrElse(
                customerId -> createNotificationUseCase.create(new CreateNotificationCommand(customerId, type, title, content)),
                () -> log.warn("Could not resolve customer for order: {}, skipping {} notification", orderId, type)
        );
    }
}
