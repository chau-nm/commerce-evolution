package dev.chaunm.commerceevolution.customer.application.usecase.address.setdefaultaddress;

import java.util.UUID;

public record SetDefaultAddressResult(
        UUID addressId,
        boolean isDefault
) {}
