package dev.chaunm.commerceevolution.catalog.domain.repository;

import dev.chaunm.commerceevolution.catalog.domain.model.Product;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.ProductId;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.ProductStatus;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.SKU;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.Slug;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationQuery;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationResult;

import java.util.Optional;

public interface ProductRepository {
    boolean existsBySlug(Slug slug);
    boolean existsBySlugAndIdNot(Slug slug, ProductId id);
    boolean existsByVariantSku(SKU sku);
    Optional<Product> findById(ProductId id);
    PaginationResult<Product> findAll(ProductStatus status, PaginationQuery query);
    Product save(Product product);
}
