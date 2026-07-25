package dev.chaunm.commerceevolution.inventory.application.usecase.getinventory;

import dev.chaunm.commerceevolution.inventory.application.usecase.createinventory.CreateInventoryCommand;
import dev.chaunm.commerceevolution.inventory.application.usecase.createinventory.CreateInventoryUseCase;
import dev.chaunm.commerceevolution.inventory.application.usecase.reservestock.ReserveStockCommand;
import dev.chaunm.commerceevolution.inventory.application.usecase.reservestock.ReserveStockUseCase;
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
class GetInventoryUseCaseImplTest {

    @Autowired
    private CreateInventoryUseCase createInventoryUseCase;

    @Autowired
    private ReserveStockUseCase reserveStockUseCase;

    @Autowired
    private GetInventoryUseCase getInventoryUseCase;

    @Test
    void returnsTheCurrentStockLevelsForTheVariant() {
        UUID variantId = UUID.randomUUID();
        createInventoryUseCase.create(new CreateInventoryCommand(variantId, 10));
        reserveStockUseCase.reserve(new ReserveStockCommand(variantId, 4));

        GetInventoryResult result = getInventoryUseCase.getInventory(new GetInventoryCommand(variantId));

        assertThat(result.variantId()).isEqualTo(variantId);
        assertThat(result.availableQuantity()).isEqualTo(6);
        assertThat(result.reservedQuantity()).isEqualTo(4);
    }

    @Test
    void throwsWhenNoInventoryExistsForTheVariant() {
        assertThatThrownBy(() -> getInventoryUseCase.getInventory(new GetInventoryCommand(UUID.randomUUID())))
                .isInstanceOf(InventoryNotFoundException.class);
    }
}
