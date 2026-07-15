package dev.chaunm.commerceevolution.customer.application.usecase.address.addaddress;

public record AddAddressCommand(
        String recipientName,
        String phoneNumber,
        String province,
        String district,
        String ward,
        String street,
        String postalCode,
        boolean isDefault
) {}
