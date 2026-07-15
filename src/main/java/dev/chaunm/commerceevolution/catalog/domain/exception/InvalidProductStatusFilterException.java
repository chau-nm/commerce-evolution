package dev.chaunm.commerceevolution.catalog.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class InvalidProductStatusFilterException extends DomainException {
    public InvalidProductStatusFilterException(String value) {
        super(CatalogErrorCode.INVALID_STATUS_FILTER, "Invalid product status filter: " + value);
    }
}
