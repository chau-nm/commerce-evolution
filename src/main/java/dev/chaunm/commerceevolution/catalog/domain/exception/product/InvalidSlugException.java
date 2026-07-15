package dev.chaunm.commerceevolution.catalog.domain.exception.product;

import dev.chaunm.commerceevolution.catalog.domain.exception.CatalogErrorCode;
import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class InvalidSlugException extends DomainException {
    public InvalidSlugException(String value) {
        super(CatalogErrorCode.INVALID_SLUG, "Invalid slug: " + value);
    }
}
