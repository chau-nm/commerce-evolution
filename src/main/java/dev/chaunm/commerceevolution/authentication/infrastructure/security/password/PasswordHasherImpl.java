package dev.chaunm.commerceevolution.authentication.infrastructure.security.password;

import dev.chaunm.commerceevolution.authentication.domain.model.valueobject.HashPassword;
import dev.chaunm.commerceevolution.authentication.domain.service.PasswordHasher;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordHasherImpl implements PasswordHasher {

    private final PasswordEncoder passwordEncoder;

    @Override
    public HashPassword hash(String password) {
        return new HashPassword(passwordEncoder.encode(password));
    }

    @Override
    public boolean matches(String password, HashPassword hashedPassword) {
        return passwordEncoder.matches(password, hashedPassword.value());
    }
}
