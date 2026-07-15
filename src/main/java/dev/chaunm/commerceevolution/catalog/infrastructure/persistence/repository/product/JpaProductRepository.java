package dev.chaunm.commerceevolution.catalog.infrastructure.persistence.repository.product;

import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductStatus;
import dev.chaunm.commerceevolution.catalog.infrastructure.persistence.entity.product.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaProductRepository extends JpaRepository<ProductEntity, UUID> {
    boolean existsBySlug(String slug);
    boolean existsBySlugAndIdNot(String slug, UUID id);
    Page<ProductEntity> findByDeletedAtIsNull(Pageable pageable);
    Page<ProductEntity> findByStatusAndDeletedAtIsNull(ProductStatus status, Pageable pageable);
}
