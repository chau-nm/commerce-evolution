package dev.chaunm.commerceevolution.catalog.domain.exception.media;

import dev.chaunm.commerceevolution.catalog.domain.exception.CatalogErrorCode;
import dev.chaunm.commerceevolution.shared.domain.exception.ConflictException;

public class MediaAlreadyPrimaryException extends ConflictException {
    public MediaAlreadyPrimaryException() {
        super(CatalogErrorCode.MEDIA_ALREADY_PRIMARY, "Media is already the thumbnail");
    }
}
