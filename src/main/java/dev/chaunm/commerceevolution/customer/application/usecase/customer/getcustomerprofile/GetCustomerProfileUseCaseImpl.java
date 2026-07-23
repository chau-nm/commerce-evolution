package dev.chaunm.commerceevolution.customer.application.usecase.customer.getcustomerprofile;

import dev.chaunm.commerceevolution.customer.domain.exception.customer.CustomerNotFoundException;
import dev.chaunm.commerceevolution.customer.domain.model.address.Address;
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

    @Override
    @Transactional(readOnly = true)
    public GetCustomerProfileResult getProfile() {
        Customer customer = customerRepository.findByAccountId(new AccountId(currentUserProvider.getCurrentUser().accountId()))
                .orElseThrow(CustomerNotFoundException::new);

        return new GetCustomerProfileResult(
                customer.getId().value(),
                customer.getAccountId().value(),
                customer.getFullName().value(),
                customer.getPhoneNumber().value(),
                customer.getBirthday(),
                customer.getGender(),
                customer.getAddresses().stream()
                        .map(this::toAddressItem)
                        .toList()
        );
    }

    private GetCustomerProfileResult.AddressItem toAddressItem(Address address) {
        return new GetCustomerProfileResult.AddressItem(
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
