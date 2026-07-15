package dev.chaunm.commerceevolution.order.application.usecase.markorderpaid;

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
public class MarkOrderPaidUseCaseImpl implements MarkOrderPaidUseCase {

    private final OrderRepository orderRepository;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public MarkOrderPaidResult markPaid(MarkOrderPaidCommand command) {
        Order order = orderRepository.findById(new OrderId(command.orderId()))
                .orElseThrow(OrderNotFoundException::new);

        order.markPaid();

        orderRepository.save(order);
        order.domainEvents().forEach(domainEventPublisher::publish);

        return new MarkOrderPaidResult(order.getId().value(), order.getStatus().name());
    }
}
