package dev.chaunm.commerceevolution.catalog.domain.repository.brand;

import dev.chaunm.commerceevolution.catalog.domain.model.brand.Brand;
import dev.chaunm.commerceevolution.catalog.domain.model.brand.valueobject.BrandId;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationQuery;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationResult;

import java.util.Optional;

public interface BrandRepository {
    Optional<Brand> findById(BrandId id);
    PaginationResult<Brand> findAll(PaginationQuery query);
    Brand save(Brand brand);
}
