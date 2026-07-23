package dev.chaunm.commerceevolution.customer.application.usecase.address.updateaddress;

import java.util.UUID;

public record UpdateAddressCommand(
        UUID addressId,
        String recipientName,
        String phoneNumber,
        String province,
        String district,
        String ward,
        String street,
        String postalCode
) {}
