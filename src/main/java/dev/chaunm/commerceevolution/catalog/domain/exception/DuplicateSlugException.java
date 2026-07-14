package dev.chaunm.commerceevolution.catalog.domain.exception;

import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.Slug;
import dev.chaunm.commerceevolution.shared.exception.ConflictException;

public class DuplicateSlugException extends ConflictException {
    public DuplicateSlugException(Slug slug) {
        super(CatalogErrorCode.DUPLICATE_SLUG, "Slug " + slug.value() + " already exists");
    }
}
