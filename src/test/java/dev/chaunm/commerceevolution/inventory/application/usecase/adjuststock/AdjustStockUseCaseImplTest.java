package dev.chaunm.commerceevolution.inventory.application.usecase.adjuststock;

import dev.chaunm.commerceevolution.inventory.application.usecase.createinventory.CreateInventoryCommand;
import dev.chaunm.commerceevolution.inventory.application.usecase.createinventory.CreateInventoryUseCase;
import dev.chaunm.commerceevolution.inventory.domain.exception.InvalidQuantityException;
import dev.chaunm.commerceevolution.inventory.domain.exception.InventoryNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class AdjustStockUseCaseImplTest {

    @Autowired
    private CreateInventoryUseCase createInventoryUseCase;

    @Autowired
    private AdjustStockUseCase adjustStockUseCase;

    @Test
    void increasesAvailableQuantityByAPositiveDelta() {
        UUID variantId = UUID.randomUUID();
        createInventoryUseCase.create(new CreateInventoryCommand(variantId, 10));

        AdjustStockResult result = adjustStockUseCase.adjust(new AdjustStockCommand(variantId, 5));

        assertThat(result.availableQuantity()).isEqualTo(15);
    }

    @Test
    void decreasesAvailableQuantityByANegativeDelta() {
        UUID variantId = UUID.randomUUID();
        createInventoryUseCase.create(new CreateInventoryCommand(variantId, 10));

        AdjustStockResult result = adjustStockUseCase.adjust(new AdjustStockCommand(variantId, -4));

        assertThat(result.availableQuantity()).isEqualTo(6);
    }

    @Test
    void rejectsADeltaThatWouldMakeAvailableQuantityNegative() {
        UUID variantId = UUID.randomUUID();
        createInventoryUseCase.create(new CreateInventoryCommand(variantId, 10));

        assertThatThrownBy(() -> adjustStockUseCase.adjust(new AdjustStockCommand(variantId, -11)))
                .isInstanceOf(InvalidQuantityException.class);
    }

    @Test
    void throwsWhenNoInventoryExistsForTheVariant() {
        assertThatThrownBy(() -> adjustStockUseCase.adjust(new AdjustStockCommand(UUID.randomUUID(), 5)))
                .isInstanceOf(InventoryNotFoundException.class);
    }
}
