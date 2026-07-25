package dev.chaunm.commerceevolution.catalog.domain.exception.media;

import dev.chaunm.commerceevolution.catalog.domain.exception.CatalogErrorCode;
import dev.chaunm.commerceevolution.shared.domain.exception.NotFoundException;

public class MediaNotFoundException extends NotFoundException {
    public MediaNotFoundException() {
        super(CatalogErrorCode.MEDIA_NOT_FOUND, "Media not found");
    }
}
