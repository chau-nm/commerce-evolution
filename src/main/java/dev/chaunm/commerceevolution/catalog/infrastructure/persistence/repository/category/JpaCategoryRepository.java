package dev.chaunm.commerceevolution.catalog.infrastructure.persistence.repository.category;

import dev.chaunm.commerceevolution.catalog.infrastructure.persistence.entity.category.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaCategoryRepository extends JpaRepository<CategoryEntity, UUID> {
    Page<CategoryEntity> findByDeletedAtIsNull(Pageable pageable);
    Page<CategoryEntity> findByParentIdAndDeletedAtIsNull(UUID parentId, Pageable pageable);
}
