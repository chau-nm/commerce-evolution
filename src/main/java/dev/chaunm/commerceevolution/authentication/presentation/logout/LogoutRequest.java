package dev.chaunm.commerceevolution.authentication.presentation.logout;

import jakarta.validation.constraints.NotBlank;

public record LogoutRequest(
        @NotBlank
        String refreshToken
) {}
