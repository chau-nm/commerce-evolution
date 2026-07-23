package dev.chaunm.commerceevolution.catalog.domain.exception.brand;

import dev.chaunm.commerceevolution.catalog.domain.exception.CatalogErrorCode;
import dev.chaunm.commerceevolution.shared.domain.exception.ConflictException;

public class BrandAlreadyDeletedException extends ConflictException {
    public BrandAlreadyDeletedException() {
        super(CatalogErrorCode.BRAND_ALREADY_DELETED, "Brand is already deleted");
    }
}
