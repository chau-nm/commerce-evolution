package dev.chaunm.commerceevolution.customer.domain.event.customer;

import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.CustomerId;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.FullName;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.Gender;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.PhoneNumber;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEvent;

import java.time.LocalDate;

public record CustomerProfileUpdatedEvent(
        CustomerId customerId,
        FullName fullName,
        PhoneNumber phoneNumber,
        LocalDate birthday,
        Gender gender
) implements DomainEvent {
}
