package dev.chaunm.commerceevolution.customer.domain.exception.address;

import dev.chaunm.commerceevolution.customer.domain.exception.CustomerErrorCode;
import dev.chaunm.commerceevolution.shared.domain.exception.NotFoundException;

public class AddressNotFoundException extends NotFoundException {
    public AddressNotFoundException() {
        super(CustomerErrorCode.ADDRESS_NOT_FOUND, "Address not found");
    }
}
