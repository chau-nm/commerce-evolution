package dev.chaunm.commerceevolution.catalog.domain.repository.product;

import dev.chaunm.commerceevolution.catalog.domain.model.product.Product;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductId;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductStatus;
import dev.chaunm.commerceevolution.catalog.domain.model.variant.valueobject.SKU;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.Slug;
import dev.chaunm.commerceevolution.catalog.domain.model.variant.valueobject.VariantId;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationQuery;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationResult;

import java.util.Optional;

public interface ProductRepository {
    boolean existsBySlug(Slug slug);
    boolean existsBySlugAndIdNot(Slug slug, ProductId id);
    boolean existsByVariantSku(SKU sku);
    boolean existsByVariantSkuAndIdNot(SKU sku, VariantId id);
    Optional<Product> findById(ProductId id);
    PaginationResult<Product> findAll(ProductStatus status, PaginationQuery query);
    Product save(Product product);
}
