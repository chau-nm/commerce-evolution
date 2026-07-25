package dev.chaunm.commerceevolution.order.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class InvalidShippingAddressException extends DomainException {
    public InvalidShippingAddressException(String reason) {
        super(OrderErrorCode.INVALID_SHIPPING_ADDRESS, "Invalid shipping address: " + reason);
    }
}
