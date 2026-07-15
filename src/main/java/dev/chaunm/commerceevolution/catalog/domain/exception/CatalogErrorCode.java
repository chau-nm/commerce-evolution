package dev.chaunm.commerceevolution.catalog.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.ErrorCode;

public enum CatalogErrorCode implements ErrorCode {
    INVALID_PRODUCT_NAME,
    INVALID_SLUG,
    INVALID_SKU,
    DUPLICATE_SLUG,
    PRODUCT_NOT_FOUND,
    INVALID_STATUS_TRANSITION,
    PRODUCT_ARCHIVED,
    DUPLICATE_SKU,
    VARIANT_NOT_FOUND,
    INVALID_MEDIA_URL,
    MEDIA_NOT_FOUND,
    INVALID_STATUS_FILTER;

    @Override
    public String code() {
        return name();
    }
}
