package dev.chaunm.commerceevolution.order.application.usecase.startshipping;

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
public class StartShippingUseCaseImpl implements StartShippingUseCase {

    private final OrderRepository orderRepository;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public StartShippingResult startShipping(StartShippingCommand command) {
        Order order = orderRepository.findById(new OrderId(command.orderId()))
                .orElseThrow(OrderNotFoundException::new);

        order.startShipping();

        orderRepository.save(order);
        order.domainEvents().forEach(domainEventPublisher::publish);

        return new StartShippingResult(order.getId().value(), order.getStatus().name());
    }
}
