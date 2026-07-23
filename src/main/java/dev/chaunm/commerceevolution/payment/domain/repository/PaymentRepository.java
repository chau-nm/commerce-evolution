package dev.chaunm.commerceevolution.payment.domain.repository;

import dev.chaunm.commerceevolution.payment.domain.model.Payment;
import dev.chaunm.commerceevolution.payment.domain.model.valueobject.OrderId;
import dev.chaunm.commerceevolution.payment.domain.model.valueobject.PaymentId;

import java.util.Optional;

public interface PaymentRepository {
    boolean existsByOrderId(OrderId orderId);

    Optional<Payment> findById(PaymentId id);

    Optional<Payment> findByOrderId(OrderId orderId);

    Payment save(Payment payment);
}
