package dev.chaunm.commerceevolution.inventory.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.ConflictException;

public class InventoryAlreadyExistsException extends ConflictException {
    public InventoryAlreadyExistsException() {
        super(InventoryErrorCode.INVENTORY_ALREADY_EXISTS, "Inventory already exists for this variant");
    }
}
