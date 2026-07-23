package dev.chaunm.commerceevolution.customer.domain.model.address.valueobject;

import java.util.Objects;
import java.util.UUID;

public record AddressId(UUID value) {

    public AddressId {
        Objects.requireNonNull(value, "AddressId cannot be null");
    }

    public static AddressId generate() {
        return new AddressId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
