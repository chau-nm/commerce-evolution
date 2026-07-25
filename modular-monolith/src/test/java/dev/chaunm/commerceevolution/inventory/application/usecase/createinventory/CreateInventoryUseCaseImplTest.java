package dev.chaunm.commerceevolution.inventory.application.usecase.createinventory;

import dev.chaunm.commerceevolution.inventory.domain.exception.InvalidQuantityException;
import dev.chaunm.commerceevolution.inventory.domain.exception.InventoryAlreadyExistsException;
import dev.chaunm.commerceevolution.inventory.domain.model.valueobject.VariantId;
import dev.chaunm.commerceevolution.inventory.domain.repository.InventoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class CreateInventoryUseCaseImplTest {

    @Autowired
    private CreateInventoryUseCase createInventoryUseCase;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Test
    void createsAnInventoryForANewVariant() {
        UUID variantId = UUID.randomUUID();

        CreateInventoryResult result = createInventoryUseCase.create(new CreateInventoryCommand(variantId, 10));

        assertThat(result.inventoryId()).isNotNull();
        assertThat(result.variantId()).isEqualTo(variantId);
        assertThat(result.availableQuantity()).isEqualTo(10);
        assertThat(result.reservedQuantity()).isZero();
        assertThat(inventoryRepository.existsByVariantId(new VariantId(variantId))).isTrue();
    }

    @Test
    void rejectsCreatingASecondInventoryForTheSameVariant() {
        UUID variantId = UUID.randomUUID();
        createInventoryUseCase.create(new CreateInventoryCommand(variantId, 10));

        assertThatThrownBy(() -> createInventoryUseCase.create(new CreateInventoryCommand(variantId, 5)))
                .isInstanceOf(InventoryAlreadyExistsException.class);
    }

    @Test
    void rejectsANegativeInitialQuantity() {
        assertThatThrownBy(() -> createInventoryUseCase.create(new CreateInventoryCommand(UUID.randomUUID(), -1)))
                .isInstanceOf(InvalidQuantityException.class);
    }
}
