package dev.chaunm.commerceevolution.customer.application.usecase.customer.createcustomer;

import dev.chaunm.commerceevolution.customer.domain.exception.customer.DuplicateCustomerForAccountException;
import dev.chaunm.commerceevolution.customer.domain.factory.customer.CustomerFactory;
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
class CreateCustomerUseCaseImplTest {

    @Autowired
    private CreateCustomerUseCase createCustomerUseCase;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CurrentUserProvider currentUserProvider;

    @Test
    void createsAProfileForTheCurrentAccount() {
        CreateCustomerCommand command = new CreateCustomerCommand(
                "Nguyen Van A",
                "0901234567",
                LocalDate.of(1990, 1, 1),
                Gender.MALE
        );

        CreateCustomerResult result = createCustomerUseCase.create(command);

        assertThat(result.accountId()).isEqualTo(currentUserProvider.getCurrentUser().accountId());
        assertThat(result.fullName()).isEqualTo("Nguyen Van A");
        assertThat(customerRepository.findByAccountId(new AccountId(currentUserProvider.getCurrentUser().accountId()))).isPresent();
    }

    @Test
    void rejectsASecondProfileForTheSameAccount() {
        Customer existing = CustomerFactory.create(
                new AccountId(currentUserProvider.getCurrentUser().accountId()),
                new FullName("Existing Customer"),
                new PhoneNumber("0901234567"),
                LocalDate.of(1990, 1, 1),
                Gender.MALE
        );
        customerRepository.save(existing);

        CreateCustomerCommand command = new CreateCustomerCommand(
                "Nguyen Van B",
                "0987654321",
                LocalDate.of(1992, 2, 2),
                Gender.FEMALE
        );

        assertThatThrownBy(() -> createCustomerUseCase.create(command))
                .isInstanceOf(DuplicateCustomerForAccountException.class);
    }
}
