package dev.chaunm.commerceevolution.customer.application.usecase.address.setdefaultaddress;

import java.util.UUID;

public record SetDefaultAddressCommand(
        UUID addressId
) {}
