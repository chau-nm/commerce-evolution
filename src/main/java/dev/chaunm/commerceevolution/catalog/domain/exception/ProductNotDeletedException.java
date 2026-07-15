package dev.chaunm.commerceevolution.catalog.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class ProductNotDeletedException extends DomainException {
    public ProductNotDeletedException() {
        super(CatalogErrorCode.PRODUCT_NOT_DELETED, "Product is not deleted");
    }
}
