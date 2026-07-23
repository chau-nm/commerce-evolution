package dev.chaunm.commerceevolution.authentication.domain.model.valueobject;

import java.util.Objects;
import java.util.UUID;

public record RefreshTokenId(UUID value) {

    public RefreshTokenId {
        Objects.requireNonNull(value, "RefreshTokenId cannot be null");
    }

    public static RefreshTokenId generate() {
        return new RefreshTokenId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
