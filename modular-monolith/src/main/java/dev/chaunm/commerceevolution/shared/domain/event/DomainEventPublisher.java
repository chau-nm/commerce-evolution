package dev.chaunm.commerceevolution.shared.domain.event;

public interface DomainEventPublisher {
    void publish(DomainEvent event);
}
