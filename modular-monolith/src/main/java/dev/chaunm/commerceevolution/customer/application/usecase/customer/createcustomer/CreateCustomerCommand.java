package dev.chaunm.commerceevolution.customer.application.usecase.customer.createcustomer;

import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.Gender;

import java.time.LocalDate;

public record CreateCustomerCommand(
        String fullName,
        String phoneNumber,
        LocalDate birthday,
        Gender gender
) {}
