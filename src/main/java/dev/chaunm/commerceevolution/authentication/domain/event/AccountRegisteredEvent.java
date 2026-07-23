package dev.chaunm.commerceevolution.authentication.domain.event;

import dev.chaunm.commerceevolution.authentication.domain.model.valueobject.AccountId;
import dev.chaunm.commerceevolution.authentication.domain.model.valueobject.Email;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEvent;

public record AccountRegisteredEvent(
        AccountId accountId,
        Email email
) implements DomainEvent {
}
