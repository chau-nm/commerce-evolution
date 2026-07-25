package dev.chaunm.commerceevolution.inventory.application.usecase.reservestock;

import dev.chaunm.commerceevolution.inventory.application.usecase.createinventory.CreateInventoryCommand;
import dev.chaunm.commerceevolution.inventory.application.usecase.createinventory.CreateInventoryUseCase;
import dev.chaunm.commerceevolution.inventory.domain.exception.InsufficientAvailableStockException;
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
class ReserveStockUseCaseImplTest {

    @Autowired
    private CreateInventoryUseCase createInventoryUseCase;

    @Autowired
    private ReserveStockUseCase reserveStockUseCase;

    @Test
    void movesQuantityFromAvailableToReserved() {
        UUID variantId = UUID.randomUUID();
        createInventoryUseCase.create(new CreateInventoryCommand(variantId, 10));

        ReserveStockResult result = reserveStockUseCase.reserve(new ReserveStockCommand(variantId, 4));

        assertThat(result.availableQuantity()).isEqualTo(6);
        assertThat(result.reservedQuantity()).isEqualTo(4);
    }

    @Test
    void rejectsReservingMoreThanIsAvailable() {
        UUID variantId = UUID.randomUUID();
        createInventoryUseCase.create(new CreateInventoryCommand(variantId, 10));

        assertThatThrownBy(() -> reserveStockUseCase.reserve(new ReserveStockCommand(variantId, 11)))
                .isInstanceOf(InsufficientAvailableStockException.class);
    }

    @Test
    void rejectsANonPositiveQuantity() {
        UUID variantId = UUID.randomUUID();
        createInventoryUseCase.create(new CreateInventoryCommand(variantId, 10));

        assertThatThrownBy(() -> reserveStockUseCase.reserve(new ReserveStockCommand(variantId, 0)))
                .isInstanceOf(InvalidQuantityException.class);
    }

    @Test
    void throwsWhenNoInventoryExistsForTheVariant() {
        assertThatThrownBy(() -> reserveStockUseCase.reserve(new ReserveStockCommand(UUID.randomUUID(), 1)))
                .isInstanceOf(InventoryNotFoundException.class);
    }
}
