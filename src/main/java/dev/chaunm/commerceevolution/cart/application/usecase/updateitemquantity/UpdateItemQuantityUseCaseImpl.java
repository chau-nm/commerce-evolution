package dev.chaunm.commerceevolution.cart.application.usecase.updateitemquantity;

import dev.chaunm.commerceevolution.cart.domain.exception.CartItemNotFoundException;
import dev.chaunm.commerceevolution.cart.domain.exception.CartNotFoundException;
import dev.chaunm.commerceevolution.cart.domain.exception.CustomerNotFoundException;
import dev.chaunm.commerceevolution.cart.domain.model.Cart;
import dev.chaunm.commerceevolution.cart.domain.model.CartItem;
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
public class UpdateItemQuantityUseCaseImpl implements UpdateItemQuantityUseCase {

    private final CartRepository cartRepository;
    private final CustomerDirectory customerDirectory;
    private final CurrentUserProvider currentUserProvider;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public UpdateItemQuantityResult updateQuantity(UpdateItemQuantityCommand command) {
        CustomerId customerId = new CustomerId(
                customerDirectory.findCustomerIdByAccountId(currentUserProvider.getCurrentUser().accountId())
                        .orElseThrow(CustomerNotFoundException::new));

        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(CartNotFoundException::new);

        CartItem item = cart.getItems().stream()
                .filter(cartItem -> cartItem.getId().value().equals(command.cartItemId()))
                .findFirst()
                .orElseThrow(CartItemNotFoundException::new);

        cart.changeQuantity(item.getId(), command.quantity());

        cartRepository.save(cart);
        cart.domainEvents().forEach(domainEventPublisher::publish);

        boolean removed = command.quantity() <= 0;

        return new UpdateItemQuantityResult(
                cart.getId().value(),
                item.getId().value(),
                item.getVariantId().value(),
                removed ? 0 : command.quantity(),
                removed
        );
    }
}
