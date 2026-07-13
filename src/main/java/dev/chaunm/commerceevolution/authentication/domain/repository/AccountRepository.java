package dev.chaunm.commerceevolution.authentication.domain.repository;

import dev.chaunm.commerceevolution.authentication.domain.model.Account;

import java.util.Optional;

public interface AccountRepository {
    Optional<Account> findByEmail(String email);
}
