package dev.chaunm.commerceevolution.inventory.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class InsufficientReservedStockException extends DomainException {
    public InsufficientReservedStockException(int reserved, int requested) {
        super(
                InventoryErrorCode.INSUFFICIENT_RESERVED_STOCK,
                "Cannot release/deduct " + requested + " units: only " + reserved + " reserved"
        );
    }
}
