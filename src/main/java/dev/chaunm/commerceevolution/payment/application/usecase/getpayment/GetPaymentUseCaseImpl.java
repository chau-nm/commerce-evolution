package dev.chaunm.commerceevolution.payment.application.usecase.getpayment;

import dev.chaunm.commerceevolution.payment.domain.exception.PaymentNotFoundException;
import dev.chaunm.commerceevolution.payment.domain.model.Payment;
import dev.chaunm.commerceevolution.payment.domain.model.valueobject.OrderId;
import dev.chaunm.commerceevolution.payment.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Does not verify that the caller owns the order — this scaffold has no OrderDirectory-style
 * port yet to check that. Add one (mirroring CustomerDirectory) before exposing this beyond
 * internal/admin use.
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
