package dev.chaunm.commerceevolution.authentication.domain.exception;

import dev.chaunm.commerceevolution.shared.exception.NotFoundException;

public class AccountNotFoundException extends NotFoundException {
    public AccountNotFoundException() {
        super(AuthenticationErrorCode.ACCOUNT_NOT_FOUND, "Account not found");
    }
}
