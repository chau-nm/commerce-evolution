package dev.chaunm.commerceevolution.payment.application.usecase.confirmpayment;

import dev.chaunm.commerceevolution.order.application.usecase.markorderpaid.MarkOrderPaidCommand;
import dev.chaunm.commerceevolution.order.application.usecase.markorderpaid.MarkOrderPaidUseCase;
import dev.chaunm.commerceevolution.payment.domain.exception.PaymentNotFoundException;
import dev.chaunm.commerceevolution.payment.domain.model.Payment;
import dev.chaunm.commerceevolution.payment.domain.model.valueobject.PaymentId;
import dev.chaunm.commerceevolution.payment.domain.repository.PaymentRepository;
import dev.chaunm.commerceevolution.payment.domain.service.PaymentGateway;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Simulates the gateway telling us the outcome of a charge. In a real integration this would be
 * triggered by an inbound webhook instead of a client-initiated call, but the reaction here —
 * settle the payment, then tell order about it through order's own public use case — stays the
 * same either way.
 */
@Service
@RequiredArgsConstructor
public class ConfirmPaymentUseCaseImpl implements ConfirmPaymentUseCase {

    private final PaymentRepository paymentRepository;
    private final PaymentGateway paymentGateway;
    private final MarkOrderPaidUseCase markOrderPaidUseCase;
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

        if (charged) {
            markOrderPaidUseCase.markPaid(new MarkOrderPaidCommand(payment.getOrderId().value()));
        }

        return new ConfirmPaymentResult(saved.getId().value(), saved.getOrderId().value(), saved.getStatus().name());
    }
}
