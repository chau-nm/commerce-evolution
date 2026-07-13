package dev.chaunm.commerceevolution.authentication.infrastructure.persistence.repository;

import dev.chaunm.commerceevolution.authentication.domain.model.Account;
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
    public Optional<Account> findByEmail(String email) {
        return jpaAccountRepository.findByEmail(email)
                .map(accountMapper::toDomain);
    }
}
