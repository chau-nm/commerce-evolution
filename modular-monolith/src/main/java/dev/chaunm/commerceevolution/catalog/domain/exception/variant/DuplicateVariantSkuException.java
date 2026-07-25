package dev.chaunm.commerceevolution.catalog.domain.exception.variant;

import dev.chaunm.commerceevolution.catalog.domain.exception.CatalogErrorCode;
import dev.chaunm.commerceevolution.catalog.domain.model.variant.valueobject.SKU;
import dev.chaunm.commerceevolution.shared.domain.exception.ConflictException;

public class DuplicateVariantSkuException extends ConflictException {
    public DuplicateVariantSkuException(SKU sku) {
        super(CatalogErrorCode.DUPLICATE_SKU, "SKU " + sku.value() + " already exists");
    }
}
