package dev.chaunm.commerceevolution.order.application.port;

import dev.chaunm.commerceevolution.order.domain.model.valueobject.OrderId;
import dev.chaunm.commerceevolution.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class OrderDirectoryImpl implements OrderDirectory {

    private final OrderRepository orderRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<UUID> findCustomerIdByOrderId(UUID orderId) {
        return orderRepository.findById(new OrderId(orderId))
                .map(order -> order.getCustomerId().value());
    }
}
