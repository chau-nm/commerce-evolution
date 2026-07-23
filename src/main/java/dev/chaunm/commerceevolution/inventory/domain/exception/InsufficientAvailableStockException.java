package dev.chaunm.commerceevolution.inventory.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class InsufficientAvailableStockException extends DomainException {
    public InsufficientAvailableStockException(int available, int requested) {
        super(
                InventoryErrorCode.INSUFFICIENT_AVAILABLE_STOCK,
                "Cannot reserve " + requested + " units: only " + available + " available"
        );
    }
}
