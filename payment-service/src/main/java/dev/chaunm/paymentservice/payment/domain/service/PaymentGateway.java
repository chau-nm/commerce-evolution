package dev.chaunm.paymentservice.payment.domain.service;

import dev.chaunm.paymentservice.payment.domain.model.valueobject.Money;
import dev.chaunm.paymentservice.payment.domain.model.valueobject.PaymentId;

/**
 * Port to an external payment processor (Stripe, VNPay, ...). The infrastructure
 * implementation used today is a mock/stub — swapping in a real gateway only requires a new
 * adapter, no change to any use case.
 */
public interface PaymentGateway {
    boolean charge(PaymentId paymentId, Money amount);
}
