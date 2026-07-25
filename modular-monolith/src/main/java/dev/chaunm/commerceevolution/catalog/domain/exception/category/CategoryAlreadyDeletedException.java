package dev.chaunm.commerceevolution.catalog.domain.exception.category;

import dev.chaunm.commerceevolution.catalog.domain.exception.CatalogErrorCode;
import dev.chaunm.commerceevolution.shared.domain.exception.ConflictException;

public class CategoryAlreadyDeletedException extends ConflictException {
    public CategoryAlreadyDeletedException() {
        super(CatalogErrorCode.CATEGORY_ALREADY_DELETED, "Category is already deleted");
    }
}
