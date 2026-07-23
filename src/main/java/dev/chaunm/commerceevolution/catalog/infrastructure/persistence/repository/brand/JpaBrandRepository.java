package dev.chaunm.commerceevolution.catalog.infrastructure.persistence.repository.brand;

import dev.chaunm.commerceevolution.catalog.infrastructure.persistence.entity.brand.BrandEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaBrandRepository extends JpaRepository<BrandEntity, UUID> {
    Page<BrandEntity> findByDeletedAtIsNull(Pageable pageable);
}
