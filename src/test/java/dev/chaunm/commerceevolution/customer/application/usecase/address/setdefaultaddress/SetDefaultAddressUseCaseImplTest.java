package dev.chaunm.commerceevolution.customer.application.usecase.address.setdefaultaddress;

import dev.chaunm.commerceevolution.customer.domain.factory.customer.CustomerFactory;
import dev.chaunm.commerceevolution.customer.domain.model.address.Address;
import dev.chaunm.commerceevolution.customer.domain.model.customer.Customer;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.AccountId;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.FullName;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.Gender;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.PhoneNumber;
import dev.chaunm.commerceevolution.customer.domain.repository.customer.CustomerRepository;
import dev.chaunm.commerceevolution.shared.application.currentuser.CurrentUserProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class SetDefaultAddressUseCaseImplTest {

    @Autowired
    private SetDefaultAddressUseCase setDefaultAddressUseCase;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CurrentUserProvider currentUserProvider;

    @Test
    void changesTheDefaultAddress() {
        Customer customer = CustomerFactory.create(
                new AccountId(currentUserProvider.getCurrentUser().accountId()),
                new FullName("Nguyen Van A"),
                new PhoneNumber("0901234567"),
                LocalDate.of(1990, 1, 1),
                Gender.MALE
        );
        customer.addAddress("Home", new PhoneNumber("0901234567"), "P", "D", "W", "S", "70000", true);
        Address office = customer.addAddress("Office", new PhoneNumber("0901234567"), "P", "D", "W", "S", "70000", false);
        customerRepository.save(customer);

        SetDefaultAddressResult result = setDefaultAddressUseCase.setDefault(new SetDefaultAddressCommand(office.getId().value()));

        assertThat(result.isDefault()).isTrue();

        Customer persisted = customerRepository.findByAccountId(new AccountId(currentUserProvider.getCurrentUser().accountId())).orElseThrow();
        assertThat(persisted.getAddresses())
                .filteredOn(Address::isDefault)
                .extracting(a -> a.getId().value())
                .containsExactly(office.getId().value());
    }
}
