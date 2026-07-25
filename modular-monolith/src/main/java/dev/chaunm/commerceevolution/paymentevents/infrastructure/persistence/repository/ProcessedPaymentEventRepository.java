package dev.chaunm.commerceevolution.paymentevents.infrastructure.persistence.repository;

import dev.chaunm.commerceevolution.paymentevents.infrastructure.persistence.entity.ProcessedPaymentEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProcessedPaymentEventRepository extends JpaRepository<ProcessedPaymentEventEntity, UUID> {
}
