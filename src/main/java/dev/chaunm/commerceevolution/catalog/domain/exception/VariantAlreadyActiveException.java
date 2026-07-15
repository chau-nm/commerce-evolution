package dev.chaunm.commerceevolution.catalog.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.ConflictException;

public class VariantAlreadyActiveException extends ConflictException {
    public VariantAlreadyActiveException() {
        super(CatalogErrorCode.VARIANT_ALREADY_ACTIVE, "Variant is already active");
    }
}
