package dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject;

import java.util.Objects;
import java.util.UUID;

/**
 * Customer's own identifier for an account, deliberately decoupled from
 * authentication's AccountId type — Customer must never depend on the Account aggregate.
 */
public record AccountId(UUID value) {

    public AccountId {
        Objects.requireNonNull(value, "AccountId cannot be null");
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
