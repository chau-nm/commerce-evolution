package dev.chaunm.commerceevolution.catalog.domain.model.brand.valueobject;

import java.util.Objects;
import java.util.UUID;

public record BrandId(UUID value) {

    public BrandId {
        Objects.requireNonNull(value, "BrandId cannot be null");
    }

    public static BrandId generate() {
        return new BrandId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
