package dev.chaunm.commerceevolution.authentication.domain.model;

import dev.chaunm.commerceevolution.authentication.domain.model.valueobject.AccountId;
import dev.chaunm.commerceevolution.authentication.domain.model.valueobject.RefreshTokenId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class RefreshToken {
    private RefreshTokenId id;
    private AccountId accountId;
    private String tokenHash;
    private Instant expiresAt;
    private boolean revoked;
    private Instant createdAt;

    public boolean isActive(Instant now) {
        return !revoked && now.isBefore(expiresAt);
    }

    public void revoke() {
        this.revoked = true;
    }
}
