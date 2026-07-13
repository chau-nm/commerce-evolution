package dev.chaunm.commerceevolution.authentication.domain.exception;

import dev.chaunm.commerceevolution.shared.exception.DomainException;

public class AccountNotFoundException extends DomainException {
    public AccountNotFoundException() {
        super(AuthenticationErrorCode.ACCOUNT_NOT_FOUND, "Account not found");
    }
}
