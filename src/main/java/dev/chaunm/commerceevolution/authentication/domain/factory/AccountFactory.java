package dev.chaunm.commerceevolution.authentication.domain.factory;

import dev.chaunm.commerceevolution.authentication.domain.model.Account;
import dev.chaunm.commerceevolution.authentication.domain.model.valueobject.*;

public class AccountFactory {
    public static Account create(
        Email email,
        HashPassword password
    ) {
        return new Account(
            AccountId.generate(),
            email,
            password,
            Role.USER,
            Status.ACTIVE
        );
    }
}
