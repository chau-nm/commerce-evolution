package dev.chaunm.commerceevolution.customer.presentation.customer.updatecustomerprofile;

import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateCustomerProfileRequest(
        @NotBlank
        @Size(max = 255)
        String fullName,
        @NotBlank
        @Size(max = 20)
        String phoneNumber,
        LocalDate birthday,
        @NotNull
        Gender gender
) {}
