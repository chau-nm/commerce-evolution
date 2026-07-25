package dev.chaunm.commerceevolution.catalog.domain.exception.product;

import dev.chaunm.commerceevolution.catalog.domain.exception.CatalogErrorCode;
import dev.chaunm.commerceevolution.shared.domain.exception.NotFoundException;

public class ProductNotFoundException extends NotFoundException {
    public ProductNotFoundException() {
        super(CatalogErrorCode.PRODUCT_NOT_FOUND, "Product not found");
    }
}
