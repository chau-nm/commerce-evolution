package dev.chaunm.commerceevolution.customer.presentation.address.addaddress;

import java.util.UUID;

public record AddAddressResponse(
        UUID addressId,
        String recipientName,
        String phoneNumber,
        String province,
        String district,
        String ward,
        String street,
        String postalCode,
        boolean isDefault
) {}
