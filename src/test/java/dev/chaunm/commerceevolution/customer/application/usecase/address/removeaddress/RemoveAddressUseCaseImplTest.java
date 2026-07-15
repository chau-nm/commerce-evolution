package dev.chaunm.commerceevolution.customer.application.usecase.address.removeaddress;

import dev.chaunm.commerceevolution.customer.domain.exception.address.DefaultAddressRemovalException;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class RemoveAddressUseCaseImplTest {

    @Autowired
    private RemoveAddressUseCase removeAddressUseCase;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CurrentUserProvider currentUserProvider;

    @Test
    void removesANonDefaultAddress() {
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

        removeAddressUseCase.removeAddress(new RemoveAddressCommand(office.getId().value()));

        Customer persisted = customerRepository.findByAccountId(new AccountId(currentUserProvider.getCurrentUser().accountId())).orElseThrow();
        assertThat(persisted.getAddresses()).hasSize(1);
    }

    @Test
    void rejectsRemovingTheDefaultAddressWhileOthersRemain() {
        Customer customer = CustomerFactory.create(
                new AccountId(currentUserProvider.getCurrentUser().accountId()),
                new FullName("Nguyen Van A"),
                new PhoneNumber("0901234567"),
                LocalDate.of(1990, 1, 1),
                Gender.MALE
        );
        Address home = customer.addAddress("Home", new PhoneNumber("0901234567"), "P", "D", "W", "S", "70000", true);
        customer.addAddress("Office", new PhoneNumber("0901234567"), "P", "D", "W", "S", "70000", false);
        customerRepository.save(customer);

        RemoveAddressCommand command = new RemoveAddressCommand(home.getId().value());

        assertThatThrownBy(() -> removeAddressUseCase.removeAddress(command))
                .isInstanceOf(DefaultAddressRemovalException.class);
    }
}
