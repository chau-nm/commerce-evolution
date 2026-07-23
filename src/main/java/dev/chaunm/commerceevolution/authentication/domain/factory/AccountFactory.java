package dev.chaunm.commerceevolution.authentication.domain.factory;

import dev.chaunm.commerceevolution.authentication.domain.event.AccountRegisteredEvent;
import dev.chaunm.commerceevolution.authentication.domain.model.Account;
import dev.chaunm.commerceevolution.authentication.domain.model.valueobject.*;

public class AccountFactory {
    public static Account create(
        Email email,
        HashPassword password
    ) {

        Account account = new Account(
            AccountId.generate(),
            email,
            password,
            Role.USER,
            Status.ACTIVE
        );

        account.registerEvent(new AccountRegisteredEvent(
            account.getId(),
            account.getEmail()
        ));

        return account;
    }
}
