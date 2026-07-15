package dev.chaunm.commerceevolution.catalog.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class InvalidSkuException extends DomainException {
    public InvalidSkuException(String value) {
        super(CatalogErrorCode.INVALID_SKU, "Invalid SKU: " + value);
    }
}
