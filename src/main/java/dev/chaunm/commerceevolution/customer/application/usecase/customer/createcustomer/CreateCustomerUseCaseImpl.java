package dev.chaunm.commerceevolution.customer.application.usecase.customer.createcustomer;

import dev.chaunm.commerceevolution.customer.domain.exception.customer.DuplicateCustomerForAccountException;
import dev.chaunm.commerceevolution.customer.domain.factory.customer.CustomerFactory;
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
public class CreateCustomerUseCaseImpl implements CreateCustomerUseCase {

    private final CustomerRepository customerRepository;
    private final CurrentUserProvider currentUserProvider;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public CreateCustomerResult create(CreateCustomerCommand command) {
        AccountId accountId = new AccountId(currentUserProvider.getCurrentUser().accountId());
        if (customerRepository.existsByAccountId(accountId)) {
            throw new DuplicateCustomerForAccountException();
        }

        FullName fullName = new FullName(command.fullName());
        PhoneNumber phoneNumber = new PhoneNumber(command.phoneNumber());

        Customer customer = CustomerFactory.create(accountId, fullName, phoneNumber, command.birthday(), command.gender());

        Customer savedCustomer = customerRepository.save(customer);
        customer.domainEvents().forEach(domainEventPublisher::publish);

        return new CreateCustomerResult(
                savedCustomer.getId().value(),
                savedCustomer.getAccountId().value(),
                savedCustomer.getFullName().value(),
                savedCustomer.getPhoneNumber().value(),
                savedCustomer.getBirthday(),
                savedCustomer.getGender()
        );
    }
}
