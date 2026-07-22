package dev.chaunm.commerceevolution.authentication.application.usecase.login;

import dev.chaunm.commerceevolution.authentication.application.usecase.register.RegisterCommand;
import dev.chaunm.commerceevolution.authentication.application.usecase.register.RegisterUseCase;
import dev.chaunm.commerceevolution.authentication.domain.exception.AccountNotFoundException;
import dev.chaunm.commerceevolution.authentication.domain.exception.InvalidPasswordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class LoginUseCaseImplTest {

    @Autowired
    private LoginUseCase loginUseCase;

    @Autowired
    private RegisterUseCase registerUseCase;

    @BeforeEach
    void setUp() {
        registerUseCase.register(new RegisterCommand("nguyen.van.a@example.com", "Password123"));
    }

    @Test
    void issuesAnAccessAndRefreshTokenForValidCredentials() {
        LoginResult result = loginUseCase.login(new LoginCommand("nguyen.van.a@example.com", "Password123"));

        assertThat(result.accessToken()).isNotBlank();
        assertThat(result.refreshToken()).isNotBlank();
    }

    @Test
    void rejectsAnUnknownEmail() {
        assertThatThrownBy(() -> loginUseCase.login(new LoginCommand("unknown@example.com", "Password123")))
                .isInstanceOf(AccountNotFoundException.class);
    }

    @Test
    void rejectsTheWrongPassword() {
        assertThatThrownBy(() -> loginUseCase.login(new LoginCommand("nguyen.van.a@example.com", "WrongPassword")))
                .isInstanceOf(InvalidPasswordException.class);
    }
}
