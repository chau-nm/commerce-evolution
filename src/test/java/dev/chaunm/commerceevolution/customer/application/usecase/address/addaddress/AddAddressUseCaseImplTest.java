package dev.chaunm.commerceevolution.customer.application.usecase.address.addaddress;

import dev.chaunm.commerceevolution.customer.domain.factory.customer.CustomerFactory;
import dev.chaunm.commerceevolution.customer.domain.model.customer.Customer;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.AccountId;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.FullName;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.Gender;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.PhoneNumber;
import dev.chaunm.commerceevolution.customer.domain.repository.customer.CustomerRepository;
import dev.chaunm.commerceevolution.shared.application.currentuser.CurrentUserProvider;
import dev.chaunm.commerceevolution.shared.testsupport.TestSecurityContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class AddAddressUseCaseImplTest {

    @Autowired
    private AddAddressUseCase addAddressUseCase;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CurrentUserProvider currentUserProvider;

    @BeforeEach
    void authenticate() {
        TestSecurityContext.authenticateAs(UUID.randomUUID());
    }

    @AfterEach
    void clearAuthentication() {
        TestSecurityContext.clear();
    }

    @Test
    void addsAnAddressToTheCurrentCustomer() {
        Customer customer = CustomerFactory.create(
                new AccountId(currentUserProvider.getCurrentUser().accountId()),
                new FullName("Nguyen Van A"),
                new PhoneNumber("0901234567"),
                LocalDate.of(1990, 1, 1),
                Gender.MALE
        );
        customerRepository.save(customer);

        AddAddressCommand command = new AddAddressCommand(
                "Recipient", "0901234567", "Province", "District", "Ward", "Street", "70000", true
        );

        AddAddressResult result = addAddressUseCase.addAddress(command);

        assertThat(result.recipientName()).isEqualTo("Recipient");
        assertThat(result.isDefault()).isTrue();

        Customer persisted = customerRepository.findByAccountId(new AccountId(currentUserProvider.getCurrentUser().accountId())).orElseThrow();
        assertThat(persisted.getAddresses()).hasSize(1);
    }
}
