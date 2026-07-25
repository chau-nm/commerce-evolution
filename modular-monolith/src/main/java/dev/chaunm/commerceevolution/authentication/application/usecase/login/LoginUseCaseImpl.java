package dev.chaunm.commerceevolution.authentication.application.usecase.login;

import dev.chaunm.commerceevolution.authentication.domain.exception.AccountNotFoundException;
import dev.chaunm.commerceevolution.authentication.domain.factory.RefreshTokenFactory;
import dev.chaunm.commerceevolution.authentication.domain.model.Account;
import dev.chaunm.commerceevolution.authentication.domain.model.RefreshToken;
import dev.chaunm.commerceevolution.authentication.domain.model.valueobject.Email;
import dev.chaunm.commerceevolution.authentication.domain.repository.AccountRepository;
import dev.chaunm.commerceevolution.authentication.domain.repository.RefreshTokenRepository;
import dev.chaunm.commerceevolution.authentication.domain.service.JwtProvider;
import dev.chaunm.commerceevolution.authentication.domain.service.PasswordHasher;
import dev.chaunm.commerceevolution.authentication.domain.service.RefreshTokenGenerator;
import dev.chaunm.commerceevolution.authentication.domain.service.TokenHasher;
import dev.chaunm.commerceevolution.authentication.infrastructure.security.jwt.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {

    private final AccountRepository accountRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;
    private final PasswordHasher passwordHasher;
    private final TokenHasher tokenHasher;
    private final RefreshTokenGenerator refreshTokenGenerator;
    private final JwtProperties jwtProperties;

    @Override
    @Transactional
    public LoginResult login(LoginCommand command) {
        Account account = accountRepository.findByEmail(new Email(command.email()))
                .orElseThrow(AccountNotFoundException::new);
        account.verifyPassword(command.password(), passwordHasher);

        String rawRefreshToken = refreshTokenGenerator.generate();
        RefreshToken refreshToken = RefreshTokenFactory.create(
                account.getId(),
                tokenHasher.hash(rawRefreshToken),
                jwtProperties.refreshTokenTtl()
        );
        refreshTokenRepository.save(refreshToken);

        String accessToken = jwtProvider.generateAccessToken(account);
        return new LoginResult(accessToken, rawRefreshToken);
    }
}
