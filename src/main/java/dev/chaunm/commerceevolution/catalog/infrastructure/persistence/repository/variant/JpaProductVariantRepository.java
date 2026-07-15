package dev.chaunm.commerceevolution.catalog.infrastructure.persistence.repository.variant;

import dev.chaunm.commerceevolution.catalog.infrastructure.persistence.entity.variant.ProductVariantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaProductVariantRepository extends JpaRepository<ProductVariantEntity, UUID> {
    boolean existsBySku(String sku);
    boolean existsBySkuAndIdNot(String sku, UUID id);
}
