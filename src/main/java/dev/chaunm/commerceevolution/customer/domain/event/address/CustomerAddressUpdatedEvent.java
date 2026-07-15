package dev.chaunm.commerceevolution.customer.domain.event.address;

import dev.chaunm.commerceevolution.customer.domain.model.address.valueobject.AddressId;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.CustomerId;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEvent;

public record CustomerAddressUpdatedEvent(
        CustomerId customerId,
        AddressId addressId
) implements DomainEvent {
}
