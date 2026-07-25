package dev.chaunm.commerceevolution.order.application.usecase.getorder;

import dev.chaunm.commerceevolution.customer.application.port.CustomerDirectory;
import dev.chaunm.commerceevolution.order.domain.exception.CustomerNotFoundException;
import dev.chaunm.commerceevolution.order.domain.exception.OrderNotFoundException;
import dev.chaunm.commerceevolution.order.domain.model.Order;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.OrderId;
import dev.chaunm.commerceevolution.order.domain.repository.OrderRepository;
import dev.chaunm.commerceevolution.shared.application.currentuser.CurrentUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetOrderUseCaseImpl implements GetOrderUseCase {

    private final OrderRepository orderRepository;
    private final CustomerDirectory customerDirectory;
    private final CurrentUserProvider currentUserProvider;
    private final GetOrderMapper getOrderMapper;

    @Override
    @Transactional(readOnly = true)
    public GetOrderResult getOrder(GetOrderCommand command) {
        UUID customerId = customerDirectory.findCustomerIdByAccountId(currentUserProvider.getCurrentUser().accountId())
                .orElseThrow(CustomerNotFoundException::new);

        Order order = orderRepository.findById(new OrderId(command.orderId()))
                .orElseThrow(OrderNotFoundException::new);

        if (!order.getCustomerId().value().equals(customerId)) {
            throw new OrderNotFoundException();
        }

        return getOrderMapper.toResult(order);
    }
}
