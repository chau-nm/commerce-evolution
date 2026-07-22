package dev.chaunm.commerceevolution.authentication.application.usecase.register;

import dev.chaunm.commerceevolution.authentication.domain.exception.ExistedEmailException;
import dev.chaunm.commerceevolution.authentication.domain.exception.InvalidEmailException;
import dev.chaunm.commerceevolution.authentication.domain.model.valueobject.Email;
import dev.chaunm.commerceevolution.authentication.domain.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class RegisterUseCaseImplTest {

    @Autowired
    private RegisterUseCase registerUseCase;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void registersANewAccountWithAHashedPassword() {
        RegisterResult result = registerUseCase.register(new RegisterCommand("nguyen.van.a@example.com", "Password123"));

        assertThat(result.id()).isNotNull();
        assertThat(accountRepository.findByEmail(new Email("nguyen.van.a@example.com")))
                .isPresent()
                .hasValueSatisfying(account -> {
                    assertThat(account.getId().value()).isEqualTo(result.id());
                    assertThat(account.getHashedPassword().value()).isNotEqualTo("Password123");
                });
    }

    @Test
    void rejectsRegistrationWithAnAlreadyRegisteredEmail() {
        registerUseCase.register(new RegisterCommand("nguyen.van.a@example.com", "Password123"));

        assertThatThrownBy(() -> registerUseCase.register(new RegisterCommand("nguyen.van.a@example.com", "OtherPassword1")))
                .isInstanceOf(ExistedEmailException.class);
    }

    @Test
    void rejectsAnInvalidEmailFormat() {
        assertThatThrownBy(() -> registerUseCase.register(new RegisterCommand("not-an-email", "Password123")))
                .isInstanceOf(InvalidEmailException.class);
    }
}
