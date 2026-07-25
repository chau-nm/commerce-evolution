package dev.chaunm.commerceevolution.customer.application.usecase.address.removeaddress;

import java.util.UUID;

public record RemoveAddressCommand(
        UUID addressId
) {}
