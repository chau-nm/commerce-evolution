package dev.chaunm.commerceevolution.order.application.event;

import dev.chaunm.commerceevolution.order.application.port.PaymentServiceClient;
import dev.chaunm.commerceevolution.order.domain.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * The only coupling point between order and payment-service: reacts to order's own published
 * domain event to kick off a payment over HTTP. Runs after the order-placing transaction has
 * committed, so a payment is only ever initiated for an order that's durably persisted. Used to
 * call payment's InitiatePaymentUseCase in-process when payment lived in this JVM; now goes
 * through {@link PaymentServiceClient}, which applies timeout/retry/circuit-breaking and never
 * throws — a payment-service outage must not fail or roll back order placement.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class InitiatePaymentOnOrderCreatedEventHandler {

    private final PaymentServiceClient paymentServiceClient;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(OrderCreatedEvent event) {
        log.info("Initiating payment for order: {}", event.orderId());
        paymentServiceClient.initiatePayment(event.orderId().value(), event.totalAmount());
    }
}
