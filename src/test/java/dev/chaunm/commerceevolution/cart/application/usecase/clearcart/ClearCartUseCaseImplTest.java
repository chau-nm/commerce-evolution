package dev.chaunm.commerceevolution.cart.application.usecase.clearcart;

import dev.chaunm.commerceevolution.cart.application.usecase.additem.AddItemCommand;
import dev.chaunm.commerceevolution.cart.application.usecase.additem.AddItemUseCase;
import dev.chaunm.commerceevolution.cart.application.usecase.getcart.GetCartUseCase;
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
class ClearCartUseCaseImplTest {

    @Autowired
    private ClearCartUseCase clearCartUseCase;

    @Autowired
    private AddItemUseCase addItemUseCase;

    @Autowired
    private GetCartUseCase getCartUseCase;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CurrentUserProvider currentUserProvider;

    @AfterEach
    void clearAuthentication() {
        TestSecurityContext.clear();
    }

    @BeforeEach
    void setUp() {
        TestSecurityContext.authenticateAs(UUID.randomUUID());
        Customer customer = CustomerFactory.create(
                new AccountId(currentUserProvider.getCurrentUser().accountId()),
                new FullName("Nguyen Van A"),
                new PhoneNumber("0901234567"),
                LocalDate.of(1990, 1, 1),
                Gender.MALE
        );
        customerRepository.save(customer);

        addItemUseCase.addItem(new AddItemCommand(UUID.randomUUID(), 2));
        addItemUseCase.addItem(new AddItemCommand(UUID.randomUUID(), 1));
    }

    @Test
    void removesAllItemsFromTheCart() {
        clearCartUseCase.clearCart();

        assertThat(getCartUseCase.getCart().items()).isEmpty();
    }
}
