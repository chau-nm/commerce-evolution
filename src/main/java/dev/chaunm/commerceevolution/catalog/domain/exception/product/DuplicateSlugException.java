package dev.chaunm.commerceevolution.catalog.domain.exception.product;

import dev.chaunm.commerceevolution.catalog.domain.exception.CatalogErrorCode;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.Slug;
import dev.chaunm.commerceevolution.shared.domain.exception.ConflictException;

public class DuplicateSlugException extends ConflictException {
    public DuplicateSlugException(Slug slug) {
        super(CatalogErrorCode.DUPLICATE_SLUG, "Slug " + slug.value() + " already exists");
    }
}
