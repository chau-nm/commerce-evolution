package dev.chaunm.commerceevolution.catalog.domain.model.valueobject;

import java.util.Objects;
import java.util.UUID;

public record MediaId(UUID value) {

    public MediaId {
        Objects.requireNonNull(value, "MediaId cannot be null");
    }

    public static MediaId generate() {
        return new MediaId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
