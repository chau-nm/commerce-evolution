package dev.chaunm.commerceevolution.cart.application.usecase.getcart;

import dev.chaunm.commerceevolution.cart.domain.exception.CustomerNotFoundException;
import dev.chaunm.commerceevolution.cart.domain.model.valueobject.CustomerId;
import dev.chaunm.commerceevolution.cart.domain.repository.CartRepository;
import dev.chaunm.commerceevolution.customer.application.port.CustomerDirectory;
import dev.chaunm.commerceevolution.shared.application.currentuser.CurrentUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetCartUseCaseImpl implements GetCartUseCase {

    private final CartRepository cartRepository;
    private final CustomerDirectory customerDirectory;
    private final CurrentUserProvider currentUserProvider;
    private final GetCartMapper getCartMapper;

    @Override
    @Transactional(readOnly = true)
    public GetCartResult getCart() {
        CustomerId customerId = new CustomerId(
                customerDirectory.findCustomerIdByAccountId(currentUserProvider.getCurrentUser().accountId())
                        .orElseThrow(CustomerNotFoundException::new));

        return cartRepository.findByCustomerId(customerId)
                .map(getCartMapper::toResult)
                .orElseGet(() -> new GetCartResult(null, customerId.value(), List.of(), null));
    }
}
