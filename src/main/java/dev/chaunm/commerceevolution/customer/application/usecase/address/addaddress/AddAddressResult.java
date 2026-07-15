package dev.chaunm.commerceevolution.customer.application.usecase.address.addaddress;

import java.util.UUID;

public record AddAddressResult(
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
