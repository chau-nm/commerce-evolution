package dev.chaunm.commerceevolution.inventory.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.ErrorCode;

public enum InventoryErrorCode implements ErrorCode {
    INVENTORY_NOT_FOUND,
    INVENTORY_ALREADY_EXISTS,
    INVALID_QUANTITY,
    INSUFFICIENT_AVAILABLE_STOCK,
    INSUFFICIENT_RESERVED_STOCK;

    @Override
    public String code() {
        return name();
    }
}
