package dev.chaunm.commerceevolution.inventory.infrastructure.persistence.repository;

import dev.chaunm.commerceevolution.inventory.domain.model.Inventory;
import dev.chaunm.commerceevolution.inventory.domain.model.valueobject.VariantId;
import dev.chaunm.commerceevolution.inventory.domain.repository.InventoryRepository;
import dev.chaunm.commerceevolution.inventory.infrastructure.persistence.entity.InventoryEntity;
import dev.chaunm.commerceevolution.inventory.infrastructure.persistence.mapper.InventoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InventoryRepositoryImpl implements InventoryRepository {

    private final JpaInventoryRepository jpaInventoryRepository;
    private final InventoryMapper inventoryMapper;

    @Override
    public boolean existsByVariantId(VariantId variantId) {
        return jpaInventoryRepository.existsByVariantId(variantId.value());
    }

    @Override
    public Optional<Inventory> findByVariantId(VariantId variantId) {
        return jpaInventoryRepository.findByVariantId(variantId.value())
                .map(inventoryMapper::toDomain);
    }

    @Override
    public Inventory save(Inventory inventory) {
        // Mutate the row's own managed/persisted entity rather than mapping a fresh detached
        // instance: the domain model carries no @Version, so a freshly-mapped entity would
        // always merge with version 0 and either bypass or spuriously fail optimistic locking.
        InventoryEntity entity = jpaInventoryRepository.findById(inventory.getId().value())
                .orElseGet(() -> inventoryMapper.toEntity(inventory));
        entity.setId(inventory.getId().value());
        entity.setVariantId(inventory.getVariantId().value());
        entity.setAvailableQuantity(inventory.getAvailableQuantity());
        entity.setReservedQuantity(inventory.getReservedQuantity());

        return inventoryMapper.toDomain(jpaInventoryRepository.save(entity));
    }
}
