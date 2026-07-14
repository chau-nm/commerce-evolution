package dev.chaunm.commerceevolution.catalog.domain.exception;

import dev.chaunm.commerceevolution.shared.exception.DomainException;

public class InvalidMediaUrlException extends DomainException {
    public InvalidMediaUrlException(String url) {
        super(CatalogErrorCode.INVALID_MEDIA_URL, "Invalid media url: " + url);
    }
}
