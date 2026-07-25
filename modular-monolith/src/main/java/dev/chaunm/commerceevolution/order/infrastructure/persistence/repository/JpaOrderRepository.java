package dev.chaunm.commerceevolution.order.infrastructure.persistence.repository;

import dev.chaunm.commerceevolution.order.infrastructure.persistence.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderRepository extends JpaRepository<OrderEntity, UUID> {
    Page<OrderEntity> findByCustomerId(UUID customerId, Pageable pageable);
}
