package dev.chaunm.commerceevolution.catalog.domain.repository;

import dev.chaunm.commerceevolution.catalog.domain.model.Product;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.Slug;

public interface ProductRepository {
    boolean existsBySlug(Slug slug);
    Product save(Product product);
}
