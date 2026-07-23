package dev.chaunm.commerceevolution.catalog.domain.exception.brand;

import dev.chaunm.commerceevolution.catalog.domain.exception.CatalogErrorCode;
import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class InvalidBrandNameException extends DomainException {
    public InvalidBrandNameException(String value) {
        super(CatalogErrorCode.INVALID_BRAND_NAME, "Invalid brand name: " + value);
    }
}
