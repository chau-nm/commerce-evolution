package dev.chaunm.commerceevolution.order.domain.repository;

import dev.chaunm.commerceevolution.order.domain.model.Order;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.CustomerId;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.OrderId;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationQuery;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationResult;

import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(OrderId id);
    PaginationResult<Order> findByCustomerId(CustomerId customerId, PaginationQuery query);
}
