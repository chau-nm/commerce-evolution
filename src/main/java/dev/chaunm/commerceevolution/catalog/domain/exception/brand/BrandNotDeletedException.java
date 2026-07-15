package dev.chaunm.commerceevolution.catalog.domain.exception.brand;

import dev.chaunm.commerceevolution.catalog.domain.exception.CatalogErrorCode;
import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class BrandNotDeletedException extends DomainException {
    public BrandNotDeletedException() {
        super(CatalogErrorCode.BRAND_NOT_DELETED, "Brand is not deleted");
    }
}
