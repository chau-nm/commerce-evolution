package dev.chaunm.commerceevolution.customer.application.usecase.customer.updatecustomerprofile;

import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.Gender;

import java.time.LocalDate;
import java.util.UUID;

public record UpdateCustomerProfileResult(
        UUID customerId,
        String fullName,
        String phoneNumber,
        LocalDate birthday,
        Gender gender
) {}
