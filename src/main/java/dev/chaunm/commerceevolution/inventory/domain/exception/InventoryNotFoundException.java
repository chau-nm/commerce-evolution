package dev.chaunm.commerceevolution.inventory.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.NotFoundException;

public class InventoryNotFoundException extends NotFoundException {
    public InventoryNotFoundException() {
        super(InventoryErrorCode.INVENTORY_NOT_FOUND, "Inventory not found");
    }
}
