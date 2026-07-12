package dev.chaunm.commerceevolution.shared.domain;

import java.time.Instant;

public interface DomainEvent {

    Instant occurredOn();
}
