package dev.chaunm.commerceevolution.cart.application.usecase.removeitem;

import dev.chaunm.commerceevolution.cart.domain.exception.CartNotFoundException;
import dev.chaunm.commerceevolution.cart.domain.model.Cart;
import dev.chaunm.commerceevolution.cart.domain.model.valueobject.CartItemId;
import dev.chaunm.commerceevolution.cart.domain.model.valueobject.CustomerId;
import dev.chaunm.commerceevolution.cart.domain.repository.CartRepository;
import dev.chaunm.commerceevolution.customer.domain.exception.customer.CustomerNotFoundException;
import dev.chaunm.commerceevolution.customer.domain.model.customer.Customer;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.AccountId;
import dev.chaunm.commerceevolution.customer.domain.repository.customer.CustomerRepository;
import dev.chaunm.commerceevolution.shared.application.currentuser.CurrentUserProvider;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RemoveItemUseCaseImpl implements RemoveItemUseCase {

    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final CurrentUserProvider currentUserProvider;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public void removeItem(RemoveItemCommand command) {
        Customer customer = customerRepository.findByAccountId(new AccountId(currentUserProvider.getCurrentUser().accountId()))
                .orElseThrow(CustomerNotFoundException::new);

        CustomerId customerId = new CustomerId(customer.getId().value());

        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(CartNotFoundException::new);

        cart.removeItem(new CartItemId(command.cartItemId()));

        cartRepository.save(cart);
        cart.domainEvents().forEach(domainEventPublisher::publish);
    }
}
