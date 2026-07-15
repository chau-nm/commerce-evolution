package dev.chaunm.commerceevolution.catalog.domain.exception.category;

import dev.chaunm.commerceevolution.catalog.domain.exception.CatalogErrorCode;
import dev.chaunm.commerceevolution.shared.domain.exception.NotFoundException;

public class ParentCategoryNotFoundException extends NotFoundException {
    public ParentCategoryNotFoundException() {
        super(CatalogErrorCode.PARENT_CATEGORY_NOT_FOUND, "Parent category not found");
    }
}
