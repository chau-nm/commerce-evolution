package dev.chaunm.commerceevolution.authentication.application.usecase.register;

import dev.chaunm.commerceevolution.authentication.domain.exception.ExistedEmailException;
import dev.chaunm.commerceevolution.authentication.domain.factory.AccountFactory;
import dev.chaunm.commerceevolution.authentication.domain.model.Account;
import dev.chaunm.commerceevolution.authentication.domain.model.valueobject.Email;
import dev.chaunm.commerceevolution.authentication.domain.repository.AccountRepository;
import dev.chaunm.commerceevolution.authentication.domain.service.PasswordHasher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUseCaseImpl implements RegisterUseCase {

    private final AccountRepository accountRepository;
    private final PasswordHasher passwordHasher;

    @Override
    public RegisterResult register(RegisterCommand command) {
        Email email = new Email(command.email());
        accountRepository.findByEmail(email)
                .ifPresent(account -> { throw new ExistedEmailException(account.getEmail()); });
        Account account = AccountFactory.create(
                email,
                passwordHasher.hash(command.password())
        );
        Account savedAccount = accountRepository.save(account);
        return new RegisterResult(savedAccount.getId().value());
    }
}
