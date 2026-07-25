package dev.chaunm.paymentservice.payment.application.usecase.getpayment;

import dev.chaunm.paymentservice.payment.domain.exception.PaymentNotFoundException;
import dev.chaunm.paymentservice.payment.domain.model.Payment;
import dev.chaunm.paymentservice.payment.domain.model.valueobject.OrderId;
import dev.chaunm.paymentservice.payment.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Reachable only over the internal API key (see InternalApiKeyFilter) — there is no
 * caller-owns-this-order check yet, same caveat this use case carried inside the monolith.
 * Add real end-user auth (e.g. verifying the shared JWT public key) before exposing this
 * beyond service-to-service/admin use.
 */
@Service
@RequiredArgsConstructor
public class GetPaymentUseCaseImpl implements GetPaymentUseCase {

    private final PaymentRepository paymentRepository;

    @Override
    @Transactional(readOnly = true)
    public GetPaymentResult getByOrderId(GetPaymentCommand command) {
        Payment payment = paymentRepository.findByOrderId(new OrderId(command.orderId()))
                .orElseThrow(PaymentNotFoundException::new);

        return new GetPaymentResult(
                payment.getId().value(),
                payment.getOrderId().value(),
                payment.getAmount().amount(),
                payment.getStatus().name()
        );
    }
}
