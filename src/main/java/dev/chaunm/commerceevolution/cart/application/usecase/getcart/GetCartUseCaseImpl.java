package dev.chaunm.commerceevolution.cart.application.usecase.getcart;

import dev.chaunm.commerceevolution.cart.domain.model.Cart;
import dev.chaunm.commerceevolution.cart.domain.model.CartItem;
import dev.chaunm.commerceevolution.cart.domain.model.valueobject.CustomerId;
import dev.chaunm.commerceevolution.cart.domain.repository.CartRepository;
import dev.chaunm.commerceevolution.customer.domain.exception.customer.CustomerNotFoundException;
import dev.chaunm.commerceevolution.customer.domain.model.customer.Customer;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.AccountId;
import dev.chaunm.commerceevolution.customer.domain.repository.customer.CustomerRepository;
import dev.chaunm.commerceevolution.shared.application.currentuser.CurrentUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetCartUseCaseImpl implements GetCartUseCase {

    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final CurrentUserProvider currentUserProvider;

    @Override
    @Transactional(readOnly = true)
    public GetCartResult getCart() {
        Customer customer = customerRepository.findByAccountId(new AccountId(currentUserProvider.getCurrentUser().accountId()))
                .orElseThrow(CustomerNotFoundException::new);

        CustomerId customerId = new CustomerId(customer.getId().value());

        return cartRepository.findByCustomerId(customerId)
                .map(this::toResult)
                .orElseGet(() -> new GetCartResult(null, customerId.value(), List.of(), null));
    }

    private GetCartResult toResult(Cart cart) {
        return new GetCartResult(
                cart.getId().value(),
                cart.getCustomerId().value(),
                cart.getItems().stream().map(this::toItemResult).toList(),
                cart.getUpdatedAt()
        );
    }

    private GetCartResult.CartItemResult toItemResult(CartItem item) {
        return new GetCartResult.CartItemResult(
                item.getId().value(),
                item.getVariantId().value(),
                item.getQuantity().value()
        );
    }
}
