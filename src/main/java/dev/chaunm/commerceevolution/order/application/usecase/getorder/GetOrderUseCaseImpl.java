package dev.chaunm.commerceevolution.order.application.usecase.getorder;

import dev.chaunm.commerceevolution.customer.domain.exception.customer.CustomerNotFoundException;
import dev.chaunm.commerceevolution.customer.domain.model.customer.Customer;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.AccountId;
import dev.chaunm.commerceevolution.customer.domain.repository.customer.CustomerRepository;
import dev.chaunm.commerceevolution.order.domain.exception.OrderNotFoundException;
import dev.chaunm.commerceevolution.order.domain.model.Order;
import dev.chaunm.commerceevolution.order.domain.model.OrderItem;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.OrderId;
import dev.chaunm.commerceevolution.order.domain.repository.OrderRepository;
import dev.chaunm.commerceevolution.shared.application.currentuser.CurrentUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetOrderUseCaseImpl implements GetOrderUseCase {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final CurrentUserProvider currentUserProvider;

    @Override
    @Transactional(readOnly = true)
    public GetOrderResult getOrder(GetOrderCommand command) {
        Customer customer = customerRepository.findByAccountId(new AccountId(currentUserProvider.getCurrentUser().accountId()))
                .orElseThrow(CustomerNotFoundException::new);

        Order order = orderRepository.findById(new OrderId(command.orderId()))
                .orElseThrow(OrderNotFoundException::new);

        if (!order.getCustomerId().value().equals(customer.getId().value())) {
            throw new OrderNotFoundException();
        }

        return toResult(order);
    }

    private GetOrderResult toResult(Order order) {
        return new GetOrderResult(
                order.getId().value(),
                order.getOrderNumber().value(),
                order.getCustomerId().value(),
                order.getStatus().name(),
                order.getTotalAmount().amount(),
                new GetOrderResult.ShippingAddressResult(
                        order.getShippingAddress().recipientName(),
                        order.getShippingAddress().recipientPhone(),
                        order.getShippingAddress().province(),
                        order.getShippingAddress().district(),
                        order.getShippingAddress().ward(),
                        order.getShippingAddress().street(),
                        order.getShippingAddress().postalCode()
                ),
                order.getItems().stream().map(this::toItemResult).toList(),
                order.getCreatedAt()
        );
    }

    private GetOrderResult.OrderItemResult toItemResult(OrderItem item) {
        return new GetOrderResult.OrderItemResult(
                item.getId().value(),
                item.getVariantId().value(),
                item.getProductName(),
                item.getVariantName(),
                item.getUnitPrice().amount(),
                item.getQuantity().value(),
                item.getSubtotal().amount()
        );
    }
}
