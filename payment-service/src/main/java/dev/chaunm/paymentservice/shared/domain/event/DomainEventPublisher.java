package dev.chaunm.paymentservice.shared.domain.event;

public interface DomainEventPublisher {
    void publish(DomainEvent event);
}
