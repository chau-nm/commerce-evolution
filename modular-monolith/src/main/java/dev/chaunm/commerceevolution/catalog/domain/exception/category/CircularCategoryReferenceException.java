package dev.chaunm.commerceevolution.catalog.domain.exception.category;

import dev.chaunm.commerceevolution.catalog.domain.exception.CatalogErrorCode;
import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class CircularCategoryReferenceException extends DomainException {
    public CircularCategoryReferenceException() {
        super(CatalogErrorCode.CIRCULAR_CATEGORY_REFERENCE, "Cannot move a category under one of its own descendants");
    }
}
