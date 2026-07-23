package dev.chaunm.commerceevolution.authentication.application.usecase.logout;

import dev.chaunm.commerceevolution.authentication.application.usecase.login.LoginCommand;
import dev.chaunm.commerceevolution.authentication.application.usecase.login.LoginResult;
import dev.chaunm.commerceevolution.authentication.application.usecase.login.LoginUseCase;
import dev.chaunm.commerceevolution.authentication.application.usecase.register.RegisterCommand;
import dev.chaunm.commerceevolution.authentication.application.usecase.register.RegisterUseCase;
import dev.chaunm.commerceevolution.authentication.domain.repository.RefreshTokenRepository;
import dev.chaunm.commerceevolution.authentication.domain.service.TokenHasher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest
@Transactional
class LogoutUseCaseImplTest {

    @Autowired
    private LogoutUseCase logoutUseCase;

    @Autowired
    private LoginUseCase loginUseCase;

    @Autowired
    private RegisterUseCase registerUseCase;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private TokenHasher tokenHasher;

    @BeforeEach
    void setUp() {
        registerUseCase.register(new RegisterCommand("nguyen.van.a@example.com", "Password123"));
    }

    @Test
    void revokesAnExistingRefreshToken() {
        LoginResult login = loginUseCase.login(new LoginCommand("nguyen.van.a@example.com", "Password123"));

        logoutUseCase.logout(new LogoutCommand(login.refreshToken()));

        assertThat(refreshTokenRepository.findByTokenHash(tokenHasher.hash(login.refreshToken())))
                .isPresent()
                .hasValueSatisfying(token -> assertThat(token.isRevoked()).isTrue());
    }

    @Test
    void doesNothingForAnUnknownRefreshToken() {
        assertThatCode(() -> logoutUseCase.logout(new LogoutCommand("unknown-refresh-token")))
                .doesNotThrowAnyException();
    }
}
