package dev.chaunm.commerceevolution.order.application.usecase.cancelorder;

import dev.chaunm.commerceevolution.customer.application.port.CustomerDirectory;
import dev.chaunm.commerceevolution.inventory.application.usecase.releasestock.ReleaseStockCommand;
import dev.chaunm.commerceevolution.inventory.application.usecase.releasestock.ReleaseStockUseCase;
import dev.chaunm.commerceevolution.order.domain.exception.CustomerNotFoundException;
import dev.chaunm.commerceevolution.order.domain.exception.OrderNotFoundException;
import dev.chaunm.commerceevolution.order.domain.model.Order;
import dev.chaunm.commerceevolution.order.domain.model.OrderItem;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.OrderId;
import dev.chaunm.commerceevolution.order.domain.repository.OrderRepository;
import dev.chaunm.commerceevolution.shared.application.currentuser.CurrentUserProvider;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CancelOrderUseCaseImpl implements CancelOrderUseCase {

    private final OrderRepository orderRepository;
    private final CustomerDirectory customerDirectory;
    private final ReleaseStockUseCase releaseStockUseCase;
    private final CurrentUserProvider currentUserProvider;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public CancelOrderResult cancelOrder(CancelOrderCommand command) {
        UUID customerId = customerDirectory.findCustomerIdByAccountId(currentUserProvider.getCurrentUser().accountId())
                .orElseThrow(CustomerNotFoundException::new);

        Order order = orderRepository.findById(new OrderId(command.orderId()))
                .orElseThrow(OrderNotFoundException::new);

        if (!order.getCustomerId().value().equals(customerId)) {
            throw new OrderNotFoundException();
        }

        order.cancel();

        orderRepository.save(order);
        order.domainEvents().forEach(domainEventPublisher::publish);

        for (OrderItem item : order.getItems()) {
            releaseStockUseCase.release(new ReleaseStockCommand(item.getVariantId().value(), item.getQuantity().value()));
        }

        return new CancelOrderResult(order.getId().value(), order.getStatus().name());
    }
}
