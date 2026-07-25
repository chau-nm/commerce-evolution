package dev.chaunm.commerceevolution.notification.domain.model.valueobject;

import java.util.Objects;
import java.util.UUID;

public record NotificationId(UUID value) {
    public NotificationId {
        Objects.requireNonNull(value, "NotificationId cannot be null");
    }

    public static NotificationId generate() {
        return new NotificationId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
