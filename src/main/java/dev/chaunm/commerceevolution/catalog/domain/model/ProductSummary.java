package dev.chaunm.commerceevolution.catalog.domain.model;

import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.BrandId;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.CategoryId;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.ProductId;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.ProductName;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.ProductStatus;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.Slug;

public record ProductSummary(
        ProductId id,
        ProductName name,
        Slug slug,
        CategoryId categoryId,
        BrandId brandId,
        ProductStatus status
) {
}
