package dev.chaunm.commerceevolution.order.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

import java.util.UUID;

public class InsufficientStockException extends DomainException {
    public InsufficientStockException(UUID variantId, int requested) {
        super(
                OrderErrorCode.INSUFFICIENT_STOCK,
                "Insufficient stock for variant " + variantId + " to fulfill requested quantity " + requested
        );
    }
}
