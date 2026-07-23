package dev.chaunm.commerceevolution.authentication.presentation.refreshtoken;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
        @NotBlank
        String refreshToken
) {}
