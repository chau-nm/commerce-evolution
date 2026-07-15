package dev.chaunm.commerceevolution.catalog.domain.exception.product;

import dev.chaunm.commerceevolution.catalog.domain.exception.CatalogErrorCode;
import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class ProductNotDeletedException extends DomainException {
    public ProductNotDeletedException() {
        super(CatalogErrorCode.PRODUCT_NOT_DELETED, "Product is not deleted");
    }
}
