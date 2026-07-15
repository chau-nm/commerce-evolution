package dev.chaunm.commerceevolution.customer.application.usecase.customer.updatecustomerprofile;

import dev.chaunm.commerceevolution.customer.domain.exception.customer.CustomerNotFoundException;
import dev.chaunm.commerceevolution.customer.domain.model.customer.Customer;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.AccountId;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.FullName;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.PhoneNumber;
import dev.chaunm.commerceevolution.customer.domain.repository.customer.CustomerRepository;
import dev.chaunm.commerceevolution.shared.application.currentuser.CurrentUserProvider;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateCustomerProfileUseCaseImpl implements UpdateCustomerProfileUseCase {

    private final CustomerRepository customerRepository;
    private final CurrentUserProvider currentUserProvider;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public UpdateCustomerProfileResult update(UpdateCustomerProfileCommand command) {
        Customer customer = customerRepository.findByAccountId(new AccountId(currentUserProvider.getCurrentUser().accountId()))
                .orElseThrow(CustomerNotFoundException::new);

        FullName fullName = new FullName(command.fullName());
        PhoneNumber phoneNumber = new PhoneNumber(command.phoneNumber());

        customer.updateProfile(fullName, phoneNumber, command.birthday(), command.gender());

        Customer savedCustomer = customerRepository.save(customer);
        customer.domainEvents().forEach(domainEventPublisher::publish);

        return new UpdateCustomerProfileResult(
                savedCustomer.getId().value(),
                savedCustomer.getFullName().value(),
                savedCustomer.getPhoneNumber().value(),
                savedCustomer.getBirthday(),
                savedCustomer.getGender()
        );
    }
}
