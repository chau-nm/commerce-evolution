package dev.chaunm.commerceevolution.authentication.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class InvalidPasswordException extends DomainException {
    public InvalidPasswordException() {
        super(AuthenticationErrorCode.INVALID_PASSWORD, "Invalid password");
    }
}
