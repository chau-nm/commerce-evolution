package dev.chaunm.paymentservice.payment.infrastructure.persistence.repository;

import dev.chaunm.paymentservice.payment.infrastructure.persistence.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaPaymentRepository extends JpaRepository<PaymentEntity, UUID> {
    boolean existsByOrderId(UUID orderId);

    Optional<PaymentEntity> findByOrderId(UUID orderId);
}
