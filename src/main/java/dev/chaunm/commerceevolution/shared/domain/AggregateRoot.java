package dev.chaunm.commerceevolution.shared.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * Base class for aggregate roots. Collects domain events raised while
 * executing behavior so an application service can publish them after the
 * aggregate has been persisted.
 */
public abstract class AggregateRoot<ID> {

    private final transient List<DomainEvent> domainEvents = new ArrayList<>();

    public abstract ID getId();

    protected void registerEvent(DomainEvent event) {
        domainEvents.add(event);
    }

    public List<DomainEvent> pullDomainEvents() {
        List<DomainEvent> events = List.copyOf(domainEvents);
        domainEvents.clear();
        return Collections.unmodifiableList(events);
    }
}
