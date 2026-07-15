package dev.chaunm.commerceevolution.catalog.domain.exception.variant;

import dev.chaunm.commerceevolution.catalog.domain.exception.CatalogErrorCode;
import dev.chaunm.commerceevolution.shared.domain.exception.ConflictException;

public class VariantAlreadyInactiveException extends ConflictException {
    public VariantAlreadyInactiveException() {
        super(CatalogErrorCode.VARIANT_ALREADY_INACTIVE, "Variant is already inactive");
    }
}
