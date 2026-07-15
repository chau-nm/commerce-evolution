package dev.chaunm.commerceevolution.customer.presentation.customer.createcustomer;

import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.Gender;

import java.time.LocalDate;
import java.util.UUID;

public record CreateCustomerResponse(
        UUID customerId,
        UUID accountId,
        String fullName,
        String phoneNumber,
        LocalDate birthday,
        Gender gender
) {}
