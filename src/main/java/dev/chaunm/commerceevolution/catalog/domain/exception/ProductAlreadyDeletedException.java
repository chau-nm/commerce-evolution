package dev.chaunm.commerceevolution.catalog.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.ConflictException;

public class ProductAlreadyDeletedException extends ConflictException {
    public ProductAlreadyDeletedException() {
        super(CatalogErrorCode.PRODUCT_ALREADY_DELETED, "Product is already deleted");
    }
}
