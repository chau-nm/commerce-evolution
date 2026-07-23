package dev.chaunm.commerceevolution.payment.domain.factory;

import dev.chaunm.commerceevolution.payment.domain.event.PaymentInitiatedEvent;
import dev.chaunm.commerceevolution.payment.domain.model.Payment;
import dev.chaunm.commerceevolution.payment.domain.model.valueobject.Money;
import dev.chaunm.commerceevolution.payment.domain.model.valueobject.OrderId;
import dev.chaunm.commerceevolution.payment.domain.model.valueobject.PaymentId;
import dev.chaunm.commerceevolution.payment.domain.model.valueobject.PaymentStatus;

public class PaymentFactory {

    public static Payment initiate(OrderId orderId, Money amount) {
        Payment payment = new Payment(PaymentId.generate(), orderId, amount, PaymentStatus.PENDING);

        payment.registerEvent(new PaymentInitiatedEvent(payment.getId(), payment.getOrderId(), amount.amount()));

        return payment;
    }
}
