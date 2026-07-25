package dev.chaunm.commerceevolution.authentication.domain.model;

import dev.chaunm.commerceevolution.authentication.domain.event.AccountRegisteredEvent;
import dev.chaunm.commerceevolution.authentication.domain.exception.InvalidPasswordException;
import dev.chaunm.commerceevolution.authentication.domain.factory.AccountFactory;
import dev.chaunm.commerceevolution.authentication.domain.model.valueobject.Email;
import dev.chaunm.commerceevolution.authentication.domain.service.PasswordHasher;
import dev.chaunm.commerceevolution.authentication.infrastructure.security.password.PasswordHasherImpl;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountTest {

    private final PasswordHasher passwordHasher = new PasswordHasherImpl(new BCryptPasswordEncoder());

    @Test
    void createRegistersActiveUserAccountAndAnAccountRegisteredEvent() {
        Email email = new Email("nguyen.van.a@example.com");

        Account account = AccountFactory.create(email, passwordHasher.hash("Password123"));

        assertThat(account.getEmail()).isEqualTo(email);
        assertThat(account.getRole().name()).isEqualTo("USER");
        assertThat(account.getStatus().name()).isEqualTo("ACTIVE");
        assertThat(account.domainEvents())
                .singleElement()
                .isInstanceOf(AccountRegisteredEvent.class);
        assertThat(((AccountRegisteredEvent) account.domainEvents().getFirst()).accountId())
                .isEqualTo(account.getId());
    }

    @Test
    void verifyPasswordSucceedsForTheCorrectPassword() {
        Account account = AccountFactory.create(
                new Email("nguyen.van.a@example.com"),
                passwordHasher.hash("Password123")
        );

        account.verifyPassword("Password123", passwordHasher);
    }

    @Test
    void verifyPasswordThrowsForTheWrongPassword() {
        Account account = AccountFactory.create(
                new Email("nguyen.van.a@example.com"),
                passwordHasher.hash("Password123")
        );

        assertThatThrownBy(() -> account.verifyPassword("WrongPassword", passwordHasher))
                .isInstanceOf(InvalidPasswordException.class);
    }
}
