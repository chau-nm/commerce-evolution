package dev.chaunm.commerceevolution.authentication.domain.model;

import dev.chaunm.commerceevolution.authentication.domain.exception.InvalidPasswordException;
import dev.chaunm.commerceevolution.authentication.domain.model.valueobject.*;
import dev.chaunm.commerceevolution.authentication.domain.service.PasswordHasher;
import lombok.*;

@Getter
@AllArgsConstructor
public class Account {
    private AccountId id;
    private Email email;
    private HashPassword password;
    private Role role;
    private Status status;

    public void verifyPassword(String password, PasswordHasher passwordHasher) {
        if (!passwordHasher.matches(password, this.password)) {
            throw new InvalidPasswordException();
        }
    }
}
