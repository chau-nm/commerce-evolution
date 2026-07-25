package dev.chaunm.commerceevolution.customer.domain.exception.address;

import dev.chaunm.commerceevolution.customer.domain.exception.CustomerErrorCode;
import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class DefaultAddressRemovalException extends DomainException {
    public DefaultAddressRemovalException() {
        super(
                CustomerErrorCode.DEFAULT_ADDRESS_REMOVAL_NOT_ALLOWED,
                "Cannot remove the default address while other addresses exist; set another address as default first"
        );
    }
}
