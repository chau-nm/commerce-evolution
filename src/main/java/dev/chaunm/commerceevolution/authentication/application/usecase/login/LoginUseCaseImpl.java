package dev.chaunm.commerceevolution.authentication.application.usecase.login;

import dev.chaunm.commerceevolution.authentication.domain.exception.AccountNotFoundException;
import dev.chaunm.commerceevolution.authentication.domain.exception.InvalidPasswordException;
import dev.chaunm.commerceevolution.authentication.domain.model.Account;
import dev.chaunm.commerceevolution.authentication.domain.model.valueobject.Email;
import dev.chaunm.commerceevolution.authentication.domain.repository.AccountRepository;
import dev.chaunm.commerceevolution.authentication.domain.service.JwtProvider;
import dev.chaunm.commerceevolution.authentication.domain.service.PasswordHasher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {

    private final AccountRepository accountRepository;
    private final JwtProvider jwtProvider;
    private final PasswordHasher passwordHasher;

    @Override
    public LoginResult login(LoginCommand command) {
        Account account = accountRepository.findByEmail(new Email(command.email()))
                .orElseThrow(AccountNotFoundException::new);
        account.verifyPassword(command.password(), passwordHasher);
        String token = jwtProvider.generateAccessToken(account);
        return new LoginResult(token);
    }
}
