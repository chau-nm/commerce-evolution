package dev.chaunm.commerceevolution.catalog.domain.exception.media;

import dev.chaunm.commerceevolution.catalog.domain.exception.CatalogErrorCode;
import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class InvalidMediaUrlException extends DomainException {
    public InvalidMediaUrlException(String url) {
        super(CatalogErrorCode.INVALID_MEDIA_URL, "Invalid media url: " + url);
    }
}
