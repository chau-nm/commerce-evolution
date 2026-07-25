package dev.chaunm.commerceevolution.authentication.domain.model;

import dev.chaunm.commerceevolution.authentication.domain.factory.RefreshTokenFactory;
import dev.chaunm.commerceevolution.authentication.domain.model.valueobject.AccountId;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RefreshTokenTest {

    private final AccountId accountId = new AccountId(UUID.randomUUID());

    @Test
    void createBuildsANonRevokedTokenExpiringAfterTheGivenTtl() {
        RefreshToken token = RefreshTokenFactory.create(accountId, "hashed-token", Duration.ofMinutes(30));

        assertThat(token.isRevoked()).isFalse();
        assertThat(token.getAccountId()).isEqualTo(accountId);
        assertThat(token.getTokenHash()).isEqualTo("hashed-token");
        assertThat(token.getExpiresAt()).isAfter(Instant.now());
    }

    @Test
    void isActiveIsTrueWhenNotRevokedAndNotYetExpired() {
        RefreshToken token = RefreshTokenFactory.create(accountId, "hashed-token", Duration.ofMinutes(30));

        assertThat(token.isActive(Instant.now())).isTrue();
    }

    @Test
    void isActiveIsFalseOnceExpired() {
        RefreshToken token = RefreshTokenFactory.create(accountId, "hashed-token", Duration.ofSeconds(-1));

        assertThat(token.isActive(Instant.now())).isFalse();
    }

    @Test
    void isActiveIsFalseOnceRevoked() {
        RefreshToken token = RefreshTokenFactory.create(accountId, "hashed-token", Duration.ofMinutes(30));

        token.revoke();

        assertThat(token.isRevoked()).isTrue();
        assertThat(token.isActive(Instant.now())).isFalse();
    }
}
