package dev.chaunm.commerceevolution.order.application.usecase.completeorder;

import dev.chaunm.commerceevolution.order.domain.exception.OrderNotFoundException;
import dev.chaunm.commerceevolution.order.domain.model.Order;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.OrderId;
import dev.chaunm.commerceevolution.order.domain.repository.OrderRepository;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompleteOrderUseCaseImpl implements CompleteOrderUseCase {

    private final OrderRepository orderRepository;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public CompleteOrderResult completeOrder(CompleteOrderCommand command) {
        Order order = orderRepository.findById(new OrderId(command.orderId()))
                .orElseThrow(OrderNotFoundException::new);

        order.complete();

        orderRepository.save(order);
        order.domainEvents().forEach(domainEventPublisher::publish);

        return new CompleteOrderResult(order.getId().value(), order.getStatus().name());
    }
}
