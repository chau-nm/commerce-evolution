package dev.chaunm.commerceevolution.customer.application.port;

import java.util.Optional;
import java.util.UUID;

public interface CustomerDirectory {

    Optional<UUID> findCustomerIdByAccountId(UUID accountId);
}
