package dev.chaunm.commerceevolution.customer.application.usecase.address.updateaddress;

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
class UpdateAddressUseCaseImplTest {

    @Autowired
    private UpdateAddressUseCase updateAddressUseCase;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CurrentUserProvider currentUserProvider;

    @Test
    void updatesAnExistingAddress() {
        Customer customer = CustomerFactory.create(
                new AccountId(currentUserProvider.getCurrentUser().accountId()),
                new FullName("Nguyen Van A"),
                new PhoneNumber("0901234567"),
                LocalDate.of(1990, 1, 1),
                Gender.MALE
        );
        Address address = customer.addAddress(
                "Recipient", new PhoneNumber("0901234567"), "Province", "District", "Ward", "Street", "70000", true
        );
        customerRepository.save(customer);

        UpdateAddressCommand command = new UpdateAddressCommand(
                address.getId().value(), "New Recipient", "0987654321", "New Province", "New District", "New Ward", "New Street", "70001"
        );

        UpdateAddressResult result = updateAddressUseCase.updateAddress(command);

        assertThat(result.recipientName()).isEqualTo("New Recipient");
        assertThat(result.province()).isEqualTo("New Province");
        assertThat(result.isDefault()).isTrue();
    }
}
