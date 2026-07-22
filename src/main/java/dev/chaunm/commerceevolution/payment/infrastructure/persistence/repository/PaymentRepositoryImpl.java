package dev.chaunm.commerceevolution.payment.infrastructure.persistence.repository;

import dev.chaunm.commerceevolution.payment.domain.model.Payment;
import dev.chaunm.commerceevolution.payment.domain.model.valueobject.OrderId;
import dev.chaunm.commerceevolution.payment.domain.model.valueobject.PaymentId;
import dev.chaunm.commerceevolution.payment.domain.repository.PaymentRepository;
import dev.chaunm.commerceevolution.payment.infrastructure.persistence.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final JpaPaymentRepository jpaPaymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public boolean existsByOrderId(OrderId orderId) {
        return jpaPaymentRepository.existsByOrderId(orderId.value());
    }

    @Override
    public Optional<Payment> findById(PaymentId id) {
        return jpaPaymentRepository.findById(id.value())
                .map(paymentMapper::toDomain);
    }

    @Override
    public Optional<Payment> findByOrderId(OrderId orderId) {
        return jpaPaymentRepository.findByOrderId(orderId.value())
                .map(paymentMapper::toDomain);
    }

    @Override
    public Payment save(Payment payment) {
        return paymentMapper.toDomain(
                jpaPaymentRepository.save(paymentMapper.toEntity(payment))
        );
    }
}
