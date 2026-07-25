package dev.chaunm.paymentservice.payment.application.usecase.initiatepayment;

import dev.chaunm.paymentservice.payment.domain.exception.PaymentAlreadyExistsException;
import dev.chaunm.paymentservice.payment.domain.factory.PaymentFactory;
import dev.chaunm.paymentservice.payment.domain.model.Payment;
import dev.chaunm.paymentservice.payment.domain.model.valueobject.Money;
import dev.chaunm.paymentservice.payment.domain.model.valueobject.OrderId;
import dev.chaunm.paymentservice.payment.domain.repository.PaymentRepository;
import dev.chaunm.paymentservice.shared.domain.event.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Called synchronously over HTTP by order-service right after an order is placed (replacing
 * the in-process OrderCreatedEvent listener this used to be, back when payment lived in the
 * same JVM as order). Idempotent by construction: a retried call for the same order hits the
 * existsByOrderId guard below and surfaces as 409 Conflict, which the caller treats as
 * "already initiated" rather than a failure.
 */
@Service
@RequiredArgsConstructor
public class InitiatePaymentUseCaseImpl implements InitiatePaymentUseCase {

    private final PaymentRepository paymentRepository;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public InitiatePaymentResult initiate(InitiatePaymentCommand command) {
        OrderId orderId = new OrderId(command.orderId());
        if (paymentRepository.existsByOrderId(orderId)) {
            throw new PaymentAlreadyExistsException();
        }

        Payment payment = PaymentFactory.initiate(orderId, new Money(command.amount()));

        Payment saved = paymentRepository.save(payment);
        payment.domainEvents().forEach(domainEventPublisher::publish);

        return new InitiatePaymentResult(
                saved.getId().value(),
                saved.getOrderId().value(),
                saved.getAmount().amount(),
                saved.getStatus().name()
        );
    }
}
