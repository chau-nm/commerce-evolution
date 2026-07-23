package dev.chaunm.commerceevolution.catalog.domain.exception.variant;

import dev.chaunm.commerceevolution.catalog.domain.exception.CatalogErrorCode;
import dev.chaunm.commerceevolution.shared.domain.exception.NotFoundException;

public class VariantNotFoundException extends NotFoundException {
    public VariantNotFoundException() {
        super(CatalogErrorCode.VARIANT_NOT_FOUND, "Variant not found");
    }
}
