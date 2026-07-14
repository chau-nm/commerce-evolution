package dev.chaunm.commerceevolution.catalog.domain.exception;

import dev.chaunm.commerceevolution.shared.exception.DomainException;

public class ProductArchivedException extends DomainException {
    public ProductArchivedException() {
        super(CatalogErrorCode.PRODUCT_ARCHIVED, "Cannot modify an archived product");
    }
}
