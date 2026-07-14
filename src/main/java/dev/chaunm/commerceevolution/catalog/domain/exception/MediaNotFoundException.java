package dev.chaunm.commerceevolution.catalog.domain.exception;

import dev.chaunm.commerceevolution.shared.exception.NotFoundException;

public class MediaNotFoundException extends NotFoundException {
    public MediaNotFoundException() {
        super(CatalogErrorCode.MEDIA_NOT_FOUND, "Media not found");
    }
}
