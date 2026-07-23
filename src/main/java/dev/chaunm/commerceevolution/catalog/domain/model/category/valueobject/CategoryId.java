package dev.chaunm.commerceevolution.catalog.domain.model.category.valueobject;

import java.util.Objects;
import java.util.UUID;

public record CategoryId(UUID value) {

    public CategoryId {
        Objects.requireNonNull(value, "CategoryId cannot be null");
    }

    public static CategoryId generate() {
        return new CategoryId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
