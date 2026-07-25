package dev.chaunm.commerceevolution.catalog.domain.exception.category;

import dev.chaunm.commerceevolution.catalog.domain.exception.CatalogErrorCode;
import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class InvalidCategoryNameException extends DomainException {
    public InvalidCategoryNameException(String value) {
        super(CatalogErrorCode.INVALID_CATEGORY_NAME, "Invalid category name: " + value);
    }
}
