package dev.chaunm.commerceevolution.catalog.domain.exception.variant;

import dev.chaunm.commerceevolution.catalog.domain.exception.CatalogErrorCode;
import dev.chaunm.commerceevolution.shared.domain.exception.ConflictException;

public class VariantAlreadyActiveException extends ConflictException {
    public VariantAlreadyActiveException() {
        super(CatalogErrorCode.VARIANT_ALREADY_ACTIVE, "Variant is already active");
    }
}
