package dev.chaunm.commerceevolution.inventory.domain.repository;

import dev.chaunm.commerceevolution.inventory.domain.model.Inventory;
import dev.chaunm.commerceevolution.inventory.domain.model.valueobject.VariantId;

import java.util.Optional;

public interface InventoryRepository {
    boolean existsByVariantId(VariantId variantId);
    Optional<Inventory> findByVariantId(VariantId variantId);
    Inventory save(Inventory inventory);
}
