package dev.chaunm.commerceevolution.customer.application.usecase.customer.updatecustomerprofile;

import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.Gender;

import java.time.LocalDate;

public record UpdateCustomerProfileCommand(
        String fullName,
        String phoneNumber,
        LocalDate birthday,
        Gender gender
) {}
