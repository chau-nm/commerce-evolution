package dev.chaunm.commerceevolution.authentication.application.usecase.refreshtoken;

import dev.chaunm.commerceevolution.authentication.application.usecase.login.LoginCommand;
import dev.chaunm.commerceevolution.authentication.application.usecase.login.LoginResult;
import dev.chaunm.commerceevolution.authentication.application.usecase.login.LoginUseCase;
import dev.chaunm.commerceevolution.authentication.application.usecase.logout.LogoutCommand;
import dev.chaunm.commerceevolution.authentication.application.usecase.logout.LogoutUseCase;
import dev.chaunm.commerceevolution.authentication.application.usecase.register.RegisterCommand;
import dev.chaunm.commerceevolution.authentication.application.usecase.register.RegisterUseCase;
import dev.chaunm.commerceevolution.authentication.domain.exception.InvalidRefreshTokenException;
import dev.chaunm.commerceevolution.authentication.domain.factory.RefreshTokenFactory;
import dev.chaunm.commerceevolution.authentication.domain.model.valueobject.Email;
import dev.chaunm.commerceevolution.authentication.domain.repository.AccountRepository;
import dev.chaunm.commerceevolution.authentication.domain.repository.RefreshTokenRepository;
import dev.chaunm.commerceevolution.authentication.domain.service.TokenHasher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class RefreshTokenUseCaseImplTest {

    @Autowired
    private RefreshTokenUseCase refreshTokenUseCase;

    @Autowired
    private LoginUseCase loginUseCase;

    @Autowired
    private LogoutUseCase logoutUseCase;

    @Autowired
    private RegisterUseCase registerUseCase;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private TokenHasher tokenHasher;

    @BeforeEach
    void setUp() {
        registerUseCase.register(new RegisterCommand("nguyen.van.a@example.com", "Password123"));
    }

    @Test
    void rotatesTheRefreshTokenAndReturnsNewCredentials() {
        LoginResult login = loginUseCase.login(new LoginCommand("nguyen.van.a@example.com", "Password123"));

        RefreshTokenResult result = refreshTokenUseCase.refresh(new RefreshTokenCommand(login.refreshToken()));

        assertThat(result.accessToken()).isNotBlank();
        assertThat(result.refreshToken()).isNotBlank();
        assertThat(result.refreshToken()).isNotEqualTo(login.refreshToken());
        assertThat(refreshTokenRepository.findByTokenHash(tokenHasher.hash(login.refreshToken())))
                .isPresent()
                .hasValueSatisfying(token -> assertThat(token.isRevoked()).isTrue());
    }

    @Test
    void rejectsAnUnknownRefreshToken() {
        assertThatThrownBy(() -> refreshTokenUseCase.refresh(new RefreshTokenCommand("unknown-refresh-token")))
                .isInstanceOf(InvalidRefreshTokenException.class);
    }

    @Test
    void rejectsAnAlreadyRevokedRefreshToken() {
        LoginResult login = loginUseCase.login(new LoginCommand("nguyen.van.a@example.com", "Password123"));
        logoutUseCase.logout(new LogoutCommand(login.refreshToken()));

        assertThatThrownBy(() -> refreshTokenUseCase.refresh(new RefreshTokenCommand(login.refreshToken())))
                .isInstanceOf(InvalidRefreshTokenException.class);
    }

    @Test
    void rejectsAnExpiredRefreshToken() {
        String rawToken = "expired-refresh-token";
        var accountId = accountRepository.findByEmail(new Email("nguyen.van.a@example.com")).orElseThrow().getId();
        refreshTokenRepository.save(RefreshTokenFactory.create(accountId, tokenHasher.hash(rawToken), Duration.ofSeconds(-60)));

        assertThatThrownBy(() -> refreshTokenUseCase.refresh(new RefreshTokenCommand(rawToken)))
                .isInstanceOf(InvalidRefreshTokenException.class);
    }
}
