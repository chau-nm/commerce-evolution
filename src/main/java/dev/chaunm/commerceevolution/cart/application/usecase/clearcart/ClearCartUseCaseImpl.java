package dev.chaunm.commerceevolution.cart.application.usecase.clearcart;

import dev.chaunm.commerceevolution.cart.domain.exception.CartNotFoundException;
import dev.chaunm.commerceevolution.cart.domain.exception.CustomerNotFoundException;
import dev.chaunm.commerceevolution.cart.domain.model.Cart;
import dev.chaunm.commerceevolution.cart.domain.model.valueobject.CustomerId;
import dev.chaunm.commerceevolution.cart.domain.repository.CartRepository;
import dev.chaunm.commerceevolution.customer.application.port.CustomerDirectory;
import dev.chaunm.commerceevolution.shared.application.currentuser.CurrentUserProvider;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClearCartUseCaseImpl implements ClearCartUseCase {

    private final CartRepository cartRepository;
    private final CustomerDirectory customerDirectory;
    private final CurrentUserProvider currentUserProvider;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public void clearCart() {
        CustomerId customerId = new CustomerId(
                customerDirectory.findCustomerIdByAccountId(currentUserProvider.getCurrentUser().accountId())
                        .orElseThrow(CustomerNotFoundException::new));

        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(CartNotFoundException::new);

        cart.clear();

        cartRepository.save(cart);
        cart.domainEvents().forEach(domainEventPublisher::publish);
    }
}
