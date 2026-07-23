package dev.chaunm.commerceevolution.payment.application.event;

import dev.chaunm.commerceevolution.order.domain.event.OrderCreatedEvent;
import dev.chaunm.commerceevolution.payment.application.usecase.initiatepayment.InitiatePaymentCommand;
import dev.chaunm.commerceevolution.payment.application.usecase.initiatepayment.InitiatePaymentUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * The only coupling point between payment and order: reacts to order's own published domain
 * event (never touches Order's aggregate/repository) to kick off a payment. Runs after the
 * order-placing transaction has committed, so a payment is only ever initiated for an order
 * that's durably persisted.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class InitiatePaymentOnOrderCreatedEventHandler {

    private final InitiatePaymentUseCase initiatePaymentUseCase;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(OrderCreatedEvent event) {
        log.info("Initiating payment for order: {}", event.orderId());
        initiatePaymentUseCase.initiate(new InitiatePaymentCommand(event.orderId().value(), event.totalAmount()));
    }
}
