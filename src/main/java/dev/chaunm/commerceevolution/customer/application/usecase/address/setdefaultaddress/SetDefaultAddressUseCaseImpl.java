package dev.chaunm.commerceevolution.customer.application.usecase.address.setdefaultaddress;

import dev.chaunm.commerceevolution.customer.domain.exception.customer.CustomerNotFoundException;
import dev.chaunm.commerceevolution.customer.domain.model.address.Address;
import dev.chaunm.commerceevolution.customer.domain.model.address.valueobject.AddressId;
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
public class SetDefaultAddressUseCaseImpl implements SetDefaultAddressUseCase {

    private final CustomerRepository customerRepository;
    private final CurrentUserProvider currentUserProvider;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public SetDefaultAddressResult setDefault(SetDefaultAddressCommand command) {
        Customer customer = customerRepository.findByAccountId(new AccountId(currentUserProvider.getCurrentUser().accountId()))
                .orElseThrow(CustomerNotFoundException::new);

        Address address = customer.setDefaultAddress(new AddressId(command.addressId()));

        customerRepository.save(customer);
        customer.domainEvents().forEach(domainEventPublisher::publish);

        return new SetDefaultAddressResult(address.getId().value(), address.isDefault());
    }
}
