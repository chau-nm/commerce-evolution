package dev.chaunm.commerceevolution.payment.domain.service;

import dev.chaunm.commerceevolution.payment.domain.model.valueobject.Money;
import dev.chaunm.commerceevolution.payment.domain.model.valueobject.PaymentId;

/**
 * Port to an external payment processor (Stripe, VNPay, ...). The infrastructure
 * implementation used today is a mock/stub — swapping in a real gateway only requires a new
 * adapter, no change to any use case.
 */
public interface PaymentGateway {
    boolean charge(PaymentId paymentId, Money amount);
}
