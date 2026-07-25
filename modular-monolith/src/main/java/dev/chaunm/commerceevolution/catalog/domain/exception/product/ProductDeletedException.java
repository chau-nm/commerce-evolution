package dev.chaunm.commerceevolution.catalog.domain.exception.product;

import dev.chaunm.commerceevolution.catalog.domain.exception.CatalogErrorCode;
import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class ProductDeletedException extends DomainException {
    public ProductDeletedException() {
        super(CatalogErrorCode.PRODUCT_DELETED, "Cannot modify a deleted product");
    }
}
