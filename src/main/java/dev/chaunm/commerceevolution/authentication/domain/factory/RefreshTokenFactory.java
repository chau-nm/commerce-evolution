package dev.chaunm.commerceevolution.authentication.domain.factory;

import dev.chaunm.commerceevolution.authentication.domain.model.RefreshToken;
import dev.chaunm.commerceevolution.authentication.domain.model.valueobject.AccountId;
import dev.chaunm.commerceevolution.authentication.domain.model.valueobject.RefreshTokenId;

import java.time.Duration;
import java.time.Instant;

public class RefreshTokenFactory {
    public static RefreshToken create(
            AccountId accountId,
            String tokenHash,
            Duration ttl
    ) {
        Instant now = Instant.now();
        return new RefreshToken(
                RefreshTokenId.generate(),
                accountId,
                tokenHash,
                now.plus(ttl),
                false,
                now
        );
    }
}
