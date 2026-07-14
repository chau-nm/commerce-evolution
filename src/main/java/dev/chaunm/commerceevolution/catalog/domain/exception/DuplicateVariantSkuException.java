package dev.chaunm.commerceevolution.catalog.domain.exception;

import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.SKU;
import dev.chaunm.commerceevolution.shared.exception.ConflictException;

public class DuplicateVariantSkuException extends ConflictException {
    public DuplicateVariantSkuException(SKU sku) {
        super(CatalogErrorCode.DUPLICATE_SKU, "SKU " + sku.value() + " already exists");
    }
}
