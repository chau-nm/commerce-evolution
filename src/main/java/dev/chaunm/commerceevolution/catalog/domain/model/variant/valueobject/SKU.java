package dev.chaunm.commerceevolution.catalog.domain.model.variant.valueobject;

import dev.chaunm.commerceevolution.catalog.domain.exception.variant.InvalidSkuException;

public record SKU(String value) {

    public static final String SKU_PATTERN = "^[A-Z0-9-]{3,64}$";

    public SKU {
        if (value == null || value.isBlank() || !value.matches(SKU_PATTERN)) {
            throw new InvalidSkuException(value);
        }
    }
}
