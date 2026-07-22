package dev.chaunm.commerceevolution.payment.domain.model.valueobject;

import java.util.Objects;
import java.util.UUID;

public record PaymentId(UUID value) {

    public PaymentId {
        Objects.requireNonNull(value, "PaymentId cannot be null");
    }

    public static PaymentId generate() {
        return new PaymentId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
