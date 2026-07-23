package dev.chaunm.commerceevolution.catalog.domain.exception.brand;

import dev.chaunm.commerceevolution.catalog.domain.exception.CatalogErrorCode;
import dev.chaunm.commerceevolution.shared.domain.exception.NotFoundException;

public class BrandNotFoundException extends NotFoundException {
    public BrandNotFoundException() {
        super(CatalogErrorCode.BRAND_NOT_FOUND, "Brand not found");
    }
}
