package dev.chaunm.commerceevolution.catalog.domain.exception;

import dev.chaunm.commerceevolution.shared.exception.ErrorCode;

public enum CatalogErrorCode implements ErrorCode {
    INVALID_PRODUCT_NAME,
    INVALID_SLUG,
    INVALID_SKU,
    DUPLICATE_SLUG;

    @Override
    public String code() {
        return name();
    }
}
