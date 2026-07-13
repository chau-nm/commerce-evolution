package dev.chaunm.commerceevolution.authentication.infrastructure.persistence.repository;

import dev.chaunm.commerceevolution.authentication.domain.model.Account;
import dev.chaunm.commerceevolution.authentication.domain.model.valueobject.Email;
import dev.chaunm.commerceevolution.authentication.domain.repository.AccountRepository;
import dev.chaunm.commerceevolution.authentication.infrastructure.persistence.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

    private final JpaAccountRepository jpaAccountRepository;
    private final AccountMapper accountMapper;

    @Override
    public Optional<Account> findByEmail(Email email) {
        return jpaAccountRepository.findByEmail(email.value())
                .map(accountMapper::toDomain);
    }

    @Override
    public Account save(Account account) {
        return accountMapper.toDomain(jpaAccountRepository.save(accountMapper.toEntity(account)));
    }
}
