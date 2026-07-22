package dev.chaunm.commerceevolution.payment.infrastructure.gateway;

import dev.chaunm.commerceevolution.payment.domain.model.valueobject.Money;
import dev.chaunm.commerceevolution.payment.domain.model.valueobject.PaymentId;
import dev.chaunm.commerceevolution.payment.domain.service.PaymentGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Stand-in for a real processor (Stripe, VNPay, ...). Always succeeds — there is no real
 * charge happening. Replace with a real adapter behind the same PaymentGateway port when one
 * is integrated; no use case needs to change.
 */
@Slf4j
@Component
public class MockPaymentGateway implements PaymentGateway {

    @Override
    public boolean charge(PaymentId paymentId, Money amount) {
        log.info("Mock-charging {} for payment {}", amount.amount(), paymentId);
        return true;
    }
}
