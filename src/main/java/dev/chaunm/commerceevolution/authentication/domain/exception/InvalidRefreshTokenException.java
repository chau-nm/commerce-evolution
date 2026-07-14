package dev.chaunm.commerceevolution.authentication.domain.exception;

import dev.chaunm.commerceevolution.shared.exception.UnauthorizedException;

public class InvalidRefreshTokenException extends UnauthorizedException {
    public InvalidRefreshTokenException() {
        super(AuthenticationErrorCode.INVALID_REFRESH_TOKEN, "Invalid or expired refresh token");
    }
}
