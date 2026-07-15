package dev.chaunm.commerceevolution.catalog.domain.exception.variant;

import dev.chaunm.commerceevolution.catalog.domain.exception.CatalogErrorCode;
import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class InvalidSkuException extends DomainException {
    public InvalidSkuException(String value) {
        super(CatalogErrorCode.INVALID_SKU, "Invalid SKU: " + value);
    }
}
