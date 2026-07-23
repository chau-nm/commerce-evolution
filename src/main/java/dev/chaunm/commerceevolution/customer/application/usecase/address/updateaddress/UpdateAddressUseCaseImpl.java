package dev.chaunm.commerceevolution.customer.application.usecase.address.updateaddress;

import dev.chaunm.commerceevolution.customer.domain.exception.customer.CustomerNotFoundException;
import dev.chaunm.commerceevolution.customer.domain.model.address.Address;
import dev.chaunm.commerceevolution.customer.domain.model.address.valueobject.AddressId;
import dev.chaunm.commerceevolution.customer.domain.model.customer.Customer;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.AccountId;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.PhoneNumber;
import dev.chaunm.commerceevolution.customer.domain.repository.customer.CustomerRepository;
import dev.chaunm.commerceevolution.shared.application.currentuser.CurrentUserProvider;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateAddressUseCaseImpl implements UpdateAddressUseCase {

    private final CustomerRepository customerRepository;
    private final CurrentUserProvider currentUserProvider;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public UpdateAddressResult updateAddress(UpdateAddressCommand command) {
        Customer customer = customerRepository.findByAccountId(new AccountId(currentUserProvider.getCurrentUser().accountId()))
                .orElseThrow(CustomerNotFoundException::new);

        PhoneNumber phoneNumber = new PhoneNumber(command.phoneNumber());

        Address address = customer.updateAddress(
                new AddressId(command.addressId()),
                command.recipientName(),
                phoneNumber,
                command.province(),
                command.district(),
                command.ward(),
                command.street(),
                command.postalCode()
        );

        customerRepository.save(customer);
        customer.domainEvents().forEach(domainEventPublisher::publish);

        return new UpdateAddressResult(
                address.getId().value(),
                address.getRecipientName(),
                address.getPhoneNumber().value(),
                address.getProvince(),
                address.getDistrict(),
                address.getWard(),
                address.getStreet(),
                address.getPostalCode(),
                address.isDefault()
        );
    }
}
