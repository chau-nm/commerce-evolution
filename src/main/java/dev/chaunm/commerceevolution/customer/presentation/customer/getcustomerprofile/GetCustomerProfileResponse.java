package dev.chaunm.commerceevolution.customer.presentation.customer.getcustomerprofile;

import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.Gender;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record GetCustomerProfileResponse(
        UUID customerId,
        UUID accountId,
        String fullName,
        String phoneNumber,
        LocalDate birthday,
        Gender gender,
        List<AddressResponse> addresses
) {
    public record AddressResponse(
            UUID id,
            String recipientName,
            String phoneNumber,
            String province,
            String district,
            String ward,
            String street,
            String postalCode,
            boolean isDefault
    ) {}
}
