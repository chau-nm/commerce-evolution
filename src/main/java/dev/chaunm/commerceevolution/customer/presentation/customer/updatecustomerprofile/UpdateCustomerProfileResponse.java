package dev.chaunm.commerceevolution.customer.presentation.customer.updatecustomerprofile;

import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.Gender;

import java.time.LocalDate;
import java.util.UUID;

public record UpdateCustomerProfileResponse(
        UUID customerId,
        String fullName,
        String phoneNumber,
        LocalDate birthday,
        Gender gender
) {}
