package dev.chaunm.commerceevolution.customer.presentation.address.updateaddress;

import java.util.UUID;

public record UpdateAddressResponse(
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
