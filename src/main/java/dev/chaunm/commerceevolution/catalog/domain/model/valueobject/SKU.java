package dev.chaunm.commerceevolution.catalog.domain.model.valueobject;

import dev.chaunm.commerceevolution.catalog.domain.exception.InvalidSkuException;

public record SKU(String value) {

    public static final String SKU_PATTERN = "^[A-Z0-9-]{3,64}$";

    public SKU {
        if (value == null || value.isBlank() || !value.matches(SKU_PATTERN)) {
            throw new InvalidSkuException(value);
        }
    }
}
