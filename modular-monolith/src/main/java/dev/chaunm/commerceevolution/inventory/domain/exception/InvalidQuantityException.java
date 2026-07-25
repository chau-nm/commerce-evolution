package dev.chaunm.commerceevolution.inventory.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class InvalidQuantityException extends DomainException {
    public InvalidQuantityException(int quantity) {
        super(InventoryErrorCode.INVALID_QUANTITY, "Invalid quantity: " + quantity);
    }
}
