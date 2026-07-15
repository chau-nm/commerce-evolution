package dev.chaunm.commerceevolution.catalog.domain.service;

import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.ProductName;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.SKU;

public interface SkuGenerator {
    SKU generate(ProductName productName, String variantName);
}
