package dev.chaunm.commerceevolution.customer.application.port;

import java.util.Optional;
import java.util.UUID;

/**
 * Public API other bounded contexts use to resolve a customer identity, without depending on
 * {@code customer.domain.*} directly. Keeping this the only cross-context entry point means
 * {@code customer}'s aggregate, repository, and exception types can change freely as long as
 * this contract holds.
 */
public interface CustomerDirectory {

    Optional<UUID> findCustomerIdByAccountId(UUID accountId);
}
