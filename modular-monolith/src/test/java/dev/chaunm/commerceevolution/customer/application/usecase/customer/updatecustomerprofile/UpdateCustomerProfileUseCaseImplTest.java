package dev.chaunm.commerceevolution.customer.application.usecase.customer.updatecustomerprofile;

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
class UpdateCustomerProfileUseCaseImplTest {

    @Autowired
    private UpdateCustomerProfileUseCase updateCustomerProfileUseCase;

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
    void updatesTheProfileForTheCurrentAccount() {
        Customer customer = CustomerFactory.create(
                new AccountId(currentUserProvider.getCurrentUser().accountId()),
                new FullName("Nguyen Van A"),
                new PhoneNumber("0901234567"),
                LocalDate.of(1990, 1, 1),
                Gender.MALE
        );
        customerRepository.save(customer);

        UpdateCustomerProfileCommand command = new UpdateCustomerProfileCommand(
                "Nguyen Van B",
                "0987654321",
                LocalDate.of(1995, 5, 5),
                Gender.FEMALE
        );

        UpdateCustomerProfileResult result = updateCustomerProfileUseCase.update(command);

        assertThat(result.fullName()).isEqualTo("Nguyen Van B");
        assertThat(result.phoneNumber()).isEqualTo("0987654321");
        assertThat(result.gender()).isEqualTo(Gender.FEMALE);

        Customer persisted = customerRepository.findByAccountId(new AccountId(currentUserProvider.getCurrentUser().accountId())).orElseThrow();
        assertThat(persisted.getFullName().value()).isEqualTo("Nguyen Van B");
    }
}
