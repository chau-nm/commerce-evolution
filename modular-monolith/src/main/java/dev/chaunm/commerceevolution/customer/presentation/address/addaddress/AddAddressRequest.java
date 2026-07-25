package dev.chaunm.commerceevolution.customer.presentation.address.addaddress;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddAddressRequest(
        @NotBlank
        @Size(max = 255)
        String recipientName,
        @NotBlank
        @Size(max = 20)
        String phoneNumber,
        @NotBlank
        @Size(max = 255)
        String province,
        @NotBlank
        @Size(max = 255)
        String district,
        @NotBlank
        @Size(max = 255)
        String ward,
        @NotBlank
        @Size(max = 255)
        String street,
        @Size(max = 20)
        String postalCode,
        boolean isDefault
) {}
