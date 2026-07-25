package dev.chaunm.commerceevolution.catalog.domain.exception.media;

import dev.chaunm.commerceevolution.catalog.domain.exception.CatalogErrorCode;
import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class InvalidMediaOrderException extends DomainException {
    public InvalidMediaOrderException() {
        super(CatalogErrorCode.INVALID_MEDIA_ORDER, "Media order must contain exactly the product's existing media");
    }
}
