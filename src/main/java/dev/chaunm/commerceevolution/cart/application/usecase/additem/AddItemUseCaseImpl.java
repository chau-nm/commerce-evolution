package dev.chaunm.commerceevolution.cart.application.usecase.additem;

import dev.chaunm.commerceevolution.cart.domain.factory.CartFactory;
import dev.chaunm.commerceevolution.cart.domain.model.Cart;
import dev.chaunm.commerceevolution.cart.domain.model.CartItem;
import dev.chaunm.commerceevolution.cart.domain.model.valueobject.CustomerId;
import dev.chaunm.commerceevolution.cart.domain.model.valueobject.VariantId;
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
public class AddItemUseCaseImpl implements AddItemUseCase {

    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final CurrentUserProvider currentUserProvider;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public AddItemResult addItem(AddItemCommand command) {
        Customer customer = customerRepository.findByAccountId(new AccountId(currentUserProvider.getCurrentUser().accountId()))
                .orElseThrow(CustomerNotFoundException::new);

        CustomerId customerId = new CustomerId(customer.getId().value());

        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseGet(() -> CartFactory.create(customerId));

        CartItem item = cart.addItem(new VariantId(command.variantId()), command.quantity());

        cartRepository.save(cart);
        cart.domainEvents().forEach(domainEventPublisher::publish);

        return new AddItemResult(
                cart.getId().value(),
                item.getId().value(),
                item.getVariantId().value(),
                item.getQuantity().value()
        );
    }
}
