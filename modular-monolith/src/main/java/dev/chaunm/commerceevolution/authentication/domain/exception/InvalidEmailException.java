package dev.chaunm.commerceevolution.authentication.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class InvalidEmailException extends DomainException {

    public InvalidEmailException(String email) {
        super(AuthenticationErrorCode.INVALID_EMAIL, "Invalid email: " + email);
    }

}
