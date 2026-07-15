package dev.chaunm.commerceevolution.order.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

import java.util.UUID;

public class InsufficientStockException extends DomainException {
    public InsufficientStockException(UUID variantId, int available, int requested) {
        super(
                OrderErrorCode.INSUFFICIENT_STOCK,
                "Insufficient stock for variant " + variantId + ": available " + available + ", requested " + requested
        );
    }
}
