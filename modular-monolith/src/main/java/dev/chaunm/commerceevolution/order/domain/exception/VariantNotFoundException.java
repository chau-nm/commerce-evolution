package dev.chaunm.commerceevolution.order.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.NotFoundException;

public class VariantNotFoundException extends NotFoundException {
    public VariantNotFoundException() {
        super(OrderErrorCode.VARIANT_NOT_FOUND, "Variant not found");
    }
}
