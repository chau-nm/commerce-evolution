package dev.chaunm.commerceevolution.customer.application.usecase.customer.getcustomerprofile;

import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.Gender;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record GetCustomerProfileResult(
        UUID customerId,
        UUID accountId,
        String fullName,
        String phoneNumber,
        LocalDate birthday,
        Gender gender,
        List<AddressItem> addresses
) {
    public record AddressItem(
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
