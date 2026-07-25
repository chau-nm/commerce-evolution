package dev.chaunm.commerceevolution.order.application.port;

import java.util.Optional;
import java.util.UUID;

/**
 * Public API other bounded contexts use to resolve which customer an order belongs to, without
 * depending on {@code order.domain.*} directly. Keeping this the only cross-context entry point
 * means {@code order}'s aggregate, repository, and exception types can change freely as long as
 * this contract holds.
 */
public interface OrderDirectory {

    Optional<UUID> findCustomerIdByOrderId(UUID orderId);
}
