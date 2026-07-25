package dev.chaunm.commerceevolution.catalog.domain.exception.category;

import dev.chaunm.commerceevolution.catalog.domain.exception.CatalogErrorCode;
import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class CategoryNotDeletedException extends DomainException {
    public CategoryNotDeletedException() {
        super(CatalogErrorCode.CATEGORY_NOT_DELETED, "Category is not deleted");
    }
}
