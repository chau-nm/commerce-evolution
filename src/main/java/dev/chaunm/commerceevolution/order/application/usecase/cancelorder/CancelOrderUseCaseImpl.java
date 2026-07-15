package dev.chaunm.commerceevolution.order.application.usecase.cancelorder;

import dev.chaunm.commerceevolution.customer.domain.exception.customer.CustomerNotFoundException;
import dev.chaunm.commerceevolution.customer.domain.model.customer.Customer;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.AccountId;
import dev.chaunm.commerceevolution.customer.domain.repository.customer.CustomerRepository;
import dev.chaunm.commerceevolution.order.domain.exception.OrderNotFoundException;
import dev.chaunm.commerceevolution.order.domain.model.Order;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.OrderId;
import dev.chaunm.commerceevolution.order.domain.repository.OrderRepository;
import dev.chaunm.commerceevolution.shared.application.currentuser.CurrentUserProvider;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CancelOrderUseCaseImpl implements CancelOrderUseCase {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final CurrentUserProvider currentUserProvider;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public CancelOrderResult cancelOrder(CancelOrderCommand command) {
        Customer customer = customerRepository.findByAccountId(new AccountId(currentUserProvider.getCurrentUser().accountId()))
                .orElseThrow(CustomerNotFoundException::new);

        Order order = orderRepository.findById(new OrderId(command.orderId()))
                .orElseThrow(OrderNotFoundException::new);

        if (!order.getCustomerId().value().equals(customer.getId().value())) {
            throw new OrderNotFoundException();
        }

        order.cancel();

        orderRepository.save(order);
        order.domainEvents().forEach(domainEventPublisher::publish);

        return new CancelOrderResult(order.getId().value(), order.getStatus().name());
    }
}
