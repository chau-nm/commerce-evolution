package dev.chaunm.commerceevolution.inventory.infrastructure.persistence.repository;

import dev.chaunm.commerceevolution.inventory.infrastructure.persistence.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaInventoryRepository extends JpaRepository<InventoryEntity, UUID> {
    boolean existsByVariantId(UUID variantId);
    Optional<InventoryEntity> findByVariantId(UUID variantId);
}
