package dev.chaunm.commerceevolution.customer.application.usecase.customer.getcustomerprofile;

import dev.chaunm.commerceevolution.customer.domain.exception.customer.CustomerNotFoundException;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class GetCustomerProfileUseCaseImplTest {

    @Autowired
    private GetCustomerProfileUseCase getCustomerProfileUseCase;

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
    void returnsTheProfileWithAddressesForTheCurrentAccount() {
        Customer customer = CustomerFactory.create(
                new AccountId(currentUserProvider.getCurrentUser().accountId()),
                new FullName("Nguyen Van A"),
                new PhoneNumber("0901234567"),
                LocalDate.of(1990, 1, 1),
                Gender.MALE
        );
        customer.addAddress("Recipient", new PhoneNumber("0901234567"), "P", "D", "W", "S", "70000", true);
        customerRepository.save(customer);

        GetCustomerProfileResult result = getCustomerProfileUseCase.getProfile();

        assertThat(result.fullName()).isEqualTo("Nguyen Van A");
        assertThat(result.addresses()).hasSize(1);
        assertThat(result.addresses().getFirst().isDefault()).isTrue();
    }

    @Test
    void throwsWhenNoCustomerExistsForTheCurrentAccount() {
        assertThatThrownBy(() -> getCustomerProfileUseCase.getProfile())
                .isInstanceOf(CustomerNotFoundException.class);
    }
}
