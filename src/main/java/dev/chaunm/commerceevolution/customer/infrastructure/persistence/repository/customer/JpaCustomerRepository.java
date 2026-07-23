package dev.chaunm.commerceevolution.customer.infrastructure.persistence.repository.customer;

import dev.chaunm.commerceevolution.customer.infrastructure.persistence.entity.customer.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaCustomerRepository extends JpaRepository<CustomerEntity, UUID> {
    Optional<CustomerEntity> findByAccountId(UUID accountId);
    boolean existsByAccountId(UUID accountId);
}
