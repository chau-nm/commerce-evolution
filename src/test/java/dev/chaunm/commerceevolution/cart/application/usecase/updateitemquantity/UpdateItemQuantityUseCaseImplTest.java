package dev.chaunm.commerceevolution.cart.application.usecase.updateitemquantity;

import dev.chaunm.commerceevolution.cart.application.usecase.additem.AddItemCommand;
import dev.chaunm.commerceevolution.cart.application.usecase.additem.AddItemResult;
import dev.chaunm.commerceevolution.cart.application.usecase.additem.AddItemUseCase;
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
class UpdateItemQuantityUseCaseImplTest {

    @Autowired
    private UpdateItemQuantityUseCase updateItemQuantityUseCase;

    @Autowired
    private AddItemUseCase addItemUseCase;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CurrentUserProvider currentUserProvider;

    private UUID cartItemId;

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

        AddItemResult added = addItemUseCase.addItem(new AddItemCommand(UUID.randomUUID(), 2));
        cartItemId = added.cartItemId();
    }

    @Test
    void updatesTheQuantityOfAnExistingItem() {
        UpdateItemQuantityResult result = updateItemQuantityUseCase.updateQuantity(
                new UpdateItemQuantityCommand(cartItemId, 7)
        );

        assertThat(result.quantity()).isEqualTo(7);
        assertThat(result.removed()).isFalse();
    }

    @Test
    void settingQuantityToZeroRemovesTheItem() {
        UpdateItemQuantityResult result = updateItemQuantityUseCase.updateQuantity(
                new UpdateItemQuantityCommand(cartItemId, 0)
        );

        assertThat(result.removed()).isTrue();
    }
}
