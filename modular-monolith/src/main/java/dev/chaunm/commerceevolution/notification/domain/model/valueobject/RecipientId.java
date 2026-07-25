package dev.chaunm.commerceevolution.notification.domain.model.valueobject;

import java.util.Objects;
import java.util.UUID;

/**
 * Notification's own identifier for who a notification is addressed to, deliberately decoupled
 * from the customer bounded context's CustomerId type — notification must never depend on the
 * Customer aggregate. Correlated only through the raw UUID value carried in domain events.
 */
public record RecipientId(UUID value) {
    public RecipientId {
        Objects.requireNonNull(value, "RecipientId cannot be null");
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
