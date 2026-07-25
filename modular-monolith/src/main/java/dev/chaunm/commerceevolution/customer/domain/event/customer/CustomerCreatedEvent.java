package dev.chaunm.commerceevolution.customer.domain.event.customer;

import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.AccountId;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.CustomerId;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEvent;

public record CustomerCreatedEvent(
        CustomerId customerId,
        AccountId accountId
) implements DomainEvent {
}
