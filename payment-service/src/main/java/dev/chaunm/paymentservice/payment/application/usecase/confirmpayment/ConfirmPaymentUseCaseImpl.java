package dev.chaunm.paymentservice.payment.application.usecase.confirmpayment;

import dev.chaunm.paymentservice.payment.domain.exception.PaymentNotFoundException;
import dev.chaunm.paymentservice.payment.domain.model.Payment;
import dev.chaunm.paymentservice.payment.domain.model.valueobject.PaymentId;
import dev.chaunm.paymentservice.payment.domain.repository.PaymentRepository;
import dev.chaunm.paymentservice.payment.domain.service.PaymentGateway;
import dev.chaunm.paymentservice.shared.domain.event.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Simulates the gateway telling us the outcome of a charge. In a real integration this would be
 * triggered by an inbound webhook instead of a client-initiated call, but the reaction here —
 * settle the payment and register a domain event — stays the same either way.
 * <p>
 * Telling order-service the outcome no longer happens inline here (that was only possible when
 * payment and order shared a JVM/transaction). It now happens out-of-band, after commit, via
 * {@code PaymentSucceededEventHandler}/{@code PaymentFailedEventHandler} calling order-service's
 * HTTP API — see those classes.
 */
@Service
@RequiredArgsConstructor
public class ConfirmPaymentUseCaseImpl implements ConfirmPaymentUseCase {

    private final PaymentRepository paymentRepository;
    private final PaymentGateway paymentGateway;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public ConfirmPaymentResult confirm(ConfirmPaymentCommand command) {
        PaymentId paymentId = new PaymentId(command.paymentId());
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(PaymentNotFoundException::new);

        boolean charged = paymentGateway.charge(payment.getId(), payment.getAmount());
        if (charged) {
            payment.markSucceeded();
        } else {
            payment.markFailed();
        }

        Payment saved = paymentRepository.save(payment);
        payment.domainEvents().forEach(domainEventPublisher::publish);

        return new ConfirmPaymentResult(saved.getId().value(), saved.getOrderId().value(), saved.getStatus().name());
    }
}
