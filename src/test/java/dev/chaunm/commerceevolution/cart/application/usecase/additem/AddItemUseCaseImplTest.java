package dev.chaunm.commerceevolution.cart.application.usecase.additem;

import dev.chaunm.commerceevolution.customer.domain.factory.customer.CustomerFactory;
import dev.chaunm.commerceevolution.customer.domain.model.customer.Customer;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.AccountId;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.FullName;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.Gender;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.PhoneNumber;
import dev.chaunm.commerceevolution.customer.domain.repository.customer.CustomerRepository;
import dev.chaunm.commerceevolution.shared.application.currentuser.CurrentUserProvider;
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
class AddItemUseCaseImplTest {

    @Autowired
    private AddItemUseCase addItemUseCase;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CurrentUserProvider currentUserProvider;

    @BeforeEach
    void setUp() {
        Customer customer = CustomerFactory.create(
                new AccountId(currentUserProvider.getCurrentUser().accountId()),
                new FullName("Nguyen Van A"),
                new PhoneNumber("0901234567"),
                LocalDate.of(1990, 1, 1),
                Gender.MALE
        );
        customerRepository.save(customer);
    }

    @Test
    void createsTheCartLazilyAndAddsTheItem() {
        UUID variantId = UUID.randomUUID();

        AddItemResult result = addItemUseCase.addItem(new AddItemCommand(variantId, 2));

        assertThat(result.cartId()).isNotNull();
        assertThat(result.variantId()).isEqualTo(variantId);
        assertThat(result.quantity()).isEqualTo(2);
    }

    @Test
    void addingTheSameVariantTwiceMergesQuantities() {
        UUID variantId = UUID.randomUUID();
        addItemUseCase.addItem(new AddItemCommand(variantId, 2));

        AddItemResult result = addItemUseCase.addItem(new AddItemCommand(variantId, 3));

        assertThat(result.quantity()).isEqualTo(5);
    }
}
