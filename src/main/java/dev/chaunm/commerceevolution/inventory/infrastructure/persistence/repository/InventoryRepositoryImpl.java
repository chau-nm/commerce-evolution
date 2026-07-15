package dev.chaunm.commerceevolution.inventory.infrastructure.persistence.repository;

import dev.chaunm.commerceevolution.inventory.domain.model.Inventory;
import dev.chaunm.commerceevolution.inventory.domain.model.valueobject.VariantId;
import dev.chaunm.commerceevolution.inventory.domain.repository.InventoryRepository;
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
        return inventoryMapper.toDomain(
                jpaInventoryRepository.save(inventoryMapper.toEntity(inventory))
        );
    }
}
