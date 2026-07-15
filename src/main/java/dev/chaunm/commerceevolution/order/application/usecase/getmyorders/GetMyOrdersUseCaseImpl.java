package dev.chaunm.commerceevolution.order.application.usecase.getmyorders;

import dev.chaunm.commerceevolution.customer.domain.exception.customer.CustomerNotFoundException;
import dev.chaunm.commerceevolution.customer.domain.model.customer.Customer;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.AccountId;
import dev.chaunm.commerceevolution.customer.domain.repository.customer.CustomerRepository;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.CustomerId;
import dev.chaunm.commerceevolution.order.domain.repository.OrderRepository;
import dev.chaunm.commerceevolution.shared.application.currentuser.CurrentUserProvider;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationQuery;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetMyOrdersUseCaseImpl implements GetMyOrdersUseCase {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final CurrentUserProvider currentUserProvider;

    @Override
    @Transactional(readOnly = true)
    public PaginationResult<OrderSummaryItem> getMyOrders(GetMyOrdersCommand command) {
        Customer customer = customerRepository.findByAccountId(new AccountId(currentUserProvider.getCurrentUser().accountId()))
                .orElseThrow(CustomerNotFoundException::new);

        CustomerId customerId = new CustomerId(customer.getId().value());
        PaginationQuery query = PaginationQuery.from(command.pagination());

        return orderRepository.findByCustomerId(customerId, query)
                .map(order -> new OrderSummaryItem(
                        order.getId().value(),
                        order.getOrderNumber().value(),
                        order.getStatus().name(),
                        order.getTotalAmount().amount(),
                        order.getCreatedAt()
                ));
    }
}
