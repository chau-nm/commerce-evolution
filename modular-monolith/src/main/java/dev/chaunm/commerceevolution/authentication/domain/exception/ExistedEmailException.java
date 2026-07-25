package dev.chaunm.commerceevolution.authentication.domain.exception;

import dev.chaunm.commerceevolution.authentication.domain.model.valueobject.Email;
import dev.chaunm.commerceevolution.shared.domain.exception.ConflictException;

public class ExistedEmailException extends ConflictException {
    public ExistedEmailException(Email email) {
        super(AuthenticationErrorCode.EXISTED_EMAIL, "Email " + email.value() + " already exists");
    }
}
