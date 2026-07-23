package dev.chaunm.commerceevolution.authentication.domain.service;

import dev.chaunm.commerceevolution.authentication.domain.model.valueobject.HashPassword;

public interface PasswordHasher {
    HashPassword hash(String password);
    boolean matches(String password, HashPassword hashedPassword);
}
