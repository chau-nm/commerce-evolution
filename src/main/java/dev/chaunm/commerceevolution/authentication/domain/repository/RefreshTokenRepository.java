package dev.chaunm.commerceevolution.authentication.domain.repository;

import dev.chaunm.commerceevolution.authentication.domain.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository {
    Optional<RefreshToken> findByTokenHash(String tokenHash);
    RefreshToken save(RefreshToken refreshToken);
}
