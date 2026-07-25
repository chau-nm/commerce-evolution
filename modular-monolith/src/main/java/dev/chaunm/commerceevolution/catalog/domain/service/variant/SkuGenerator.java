package dev.chaunm.commerceevolution.catalog.domain.service.variant;

import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductName;
import dev.chaunm.commerceevolution.catalog.domain.model.variant.valueobject.SKU;

public interface SkuGenerator {
    SKU generate(ProductName productName, String variantName);
}
