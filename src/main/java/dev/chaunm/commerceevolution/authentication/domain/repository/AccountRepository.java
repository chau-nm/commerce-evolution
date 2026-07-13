package dev.chaunm.commerceevolution.authentication.domain.repository;

import dev.chaunm.commerceevolution.authentication.domain.model.Account;
import dev.chaunm.commerceevolution.authentication.domain.model.valueobject.Email;

import java.util.Optional;

public interface AccountRepository {
    Optional<Account> findByEmail(Email email);
    Account save(Account account);
}
