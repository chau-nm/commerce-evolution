package dev.chaunm.commerceevolution.catalog.domain.exception.category;

import dev.chaunm.commerceevolution.catalog.domain.exception.CatalogErrorCode;
import dev.chaunm.commerceevolution.shared.domain.exception.NotFoundException;

public class CategoryNotFoundException extends NotFoundException {
    public CategoryNotFoundException() {
        super(CatalogErrorCode.CATEGORY_NOT_FOUND, "Category not found");
    }
}
