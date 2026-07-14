package dev.chaunm.commerceevolution.catalog.domain.exception;

import dev.chaunm.commerceevolution.shared.exception.DomainException;

public class InvalidSlugException extends DomainException {
    public InvalidSlugException(String value) {
        super(CatalogErrorCode.INVALID_SLUG, "Invalid slug: " + value);
    }
}
