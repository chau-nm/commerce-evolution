package dev.chaunm.commerceevolution.authentication.infrastructure.persistence.repository;

import dev.chaunm.commerceevolution.authentication.infrastructure.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaAccountRepository extends JpaRepository<AccountEntity, UUID> {
    Optional<AccountEntity> findByEmail(String email);
}
