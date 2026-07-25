package dev.chaunm.commerceevolution.catalog.domain.exception.category;

import dev.chaunm.commerceevolution.catalog.domain.exception.CatalogErrorCode;
import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class CategoryCannotBeOwnParentException extends DomainException {
    public CategoryCannotBeOwnParentException() {
        super(CatalogErrorCode.CATEGORY_CANNOT_BE_OWN_PARENT, "A category cannot be its own parent");
    }
}
