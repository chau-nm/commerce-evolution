package dev.chaunm.commerceevolution.customer.application.usecase.customer.getcustomerprofile;

import dev.chaunm.commerceevolution.customer.domain.exception.customer.CustomerNotFoundException;
import dev.chaunm.commerceevolution.customer.domain.model.customer.Customer;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.AccountId;
import dev.chaunm.commerceevolution.customer.domain.repository.customer.CustomerRepository;
import dev.chaunm.commerceevolution.shared.application.currentuser.CurrentUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetCustomerProfileUseCaseImpl implements GetCustomerProfileUseCase {

    private final CustomerRepository customerRepository;
    private final CurrentUserProvider currentUserProvider;
    private final GetCustomerProfileMapper getCustomerProfileMapper;

    @Override
    @Transactional(readOnly = true)
    public GetCustomerProfileResult getProfile() {
        Customer customer = customerRepository.findByAccountId(new AccountId(currentUserProvider.getCurrentUser().accountId()))
                .orElseThrow(CustomerNotFoundException::new);

        return getCustomerProfileMapper.toResult(customer);
    }
}
