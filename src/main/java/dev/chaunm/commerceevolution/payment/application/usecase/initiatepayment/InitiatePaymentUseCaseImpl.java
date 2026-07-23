package dev.chaunm.commerceevolution.payment.application.usecase.initiatepayment;

import dev.chaunm.commerceevolution.payment.domain.exception.PaymentAlreadyExistsException;
import dev.chaunm.commerceevolution.payment.domain.factory.PaymentFactory;
import dev.chaunm.commerceevolution.payment.domain.model.Payment;
import dev.chaunm.commerceevolution.payment.domain.model.valueobject.Money;
import dev.chaunm.commerceevolution.payment.domain.model.valueobject.OrderId;
import dev.chaunm.commerceevolution.payment.domain.repository.PaymentRepository;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
