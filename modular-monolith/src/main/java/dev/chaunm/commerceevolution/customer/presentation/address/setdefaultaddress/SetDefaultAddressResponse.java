package dev.chaunm.commerceevolution.customer.presentation.address.setdefaultaddress;

import java.util.UUID;

public record SetDefaultAddressResponse(
        UUID addressId,
        boolean isDefault
) {}
