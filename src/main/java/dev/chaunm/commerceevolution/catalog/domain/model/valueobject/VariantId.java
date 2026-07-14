package dev.chaunm.commerceevolution.catalog.domain.model.valueobject;

import java.util.Objects;
import java.util.UUID;

public record VariantId(UUID value) {

    public VariantId {
        Objects.requireNonNull(value, "VariantId cannot be null");
    }

    public static VariantId generate() {
        return new VariantId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
