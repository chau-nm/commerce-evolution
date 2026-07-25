package dev.chaunm.commerceevolution.order.infrastructure.persistence.repository;

import dev.chaunm.commerceevolution.order.domain.model.Order;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.CustomerId;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.OrderId;
import dev.chaunm.commerceevolution.order.domain.repository.OrderRepository;
import dev.chaunm.commerceevolution.order.infrastructure.persistence.entity.OrderEntity;
import dev.chaunm.commerceevolution.order.infrastructure.persistence.mapper.OrderMapper;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationQuery;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final JpaOrderRepository jpaOrderRepository;
    private final OrderMapper orderMapper;

    @Override
    public Order save(Order order) {
        return orderMapper.toDomain(
                jpaOrderRepository.save(orderMapper.toEntity(order))
        );
    }

    @Override
    public Optional<Order> findById(OrderId id) {
        return jpaOrderRepository.findById(id.value())
                .map(orderMapper::toDomain);
    }

    @Override
    public PaginationResult<Order> findByCustomerId(CustomerId customerId, PaginationQuery query) {
        Page<OrderEntity> page = jpaOrderRepository.findByCustomerId(customerId.value(), query.toPageable());

        return PaginationResult.from(page)
                .map(orderMapper::toDomain);
    }
}
