package dev.chaunm.commerceevolution.order.domain.model.valueobject;

import dev.chaunm.commerceevolution.order.domain.exception.InvalidOrderNumberException;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public record OrderNumber(String value) {

    private static final DateTimeFormatter DATE_PATTERN =
            DateTimeFormatter.ofPattern("yyyyMMdd").withZone(ZoneOffset.UTC);

    public OrderNumber {
        if (value == null || value.isBlank()) {
            throw new InvalidOrderNumberException(value);
        }
    }

    public static OrderNumber generate() {
        String datePart = DATE_PATTERN.format(Instant.now());
        String randomPart = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        return new OrderNumber("ORD-" + datePart + "-" + randomPart);
    }

    @Override
    public String toString() {
        return value;
    }
}
