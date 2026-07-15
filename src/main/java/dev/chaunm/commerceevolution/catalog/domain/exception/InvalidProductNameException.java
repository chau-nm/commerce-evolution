package dev.chaunm.commerceevolution.catalog.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class InvalidProductNameException extends DomainException {
    public InvalidProductNameException(String value) {
        super(CatalogErrorCode.INVALID_PRODUCT_NAME, "Invalid product name: " + value);
    }
}
