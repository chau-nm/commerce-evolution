package dev.chaunm.commerceevolution.inventory.application.usecase.deductstock;

import dev.chaunm.commerceevolution.inventory.application.usecase.createinventory.CreateInventoryCommand;
import dev.chaunm.commerceevolution.inventory.application.usecase.createinventory.CreateInventoryUseCase;
import dev.chaunm.commerceevolution.inventory.application.usecase.reservestock.ReserveStockCommand;
import dev.chaunm.commerceevolution.inventory.application.usecase.reservestock.ReserveStockUseCase;
import dev.chaunm.commerceevolution.inventory.domain.exception.InsufficientReservedStockException;
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
class DeductStockUseCaseImplTest {

    @Autowired
    private CreateInventoryUseCase createInventoryUseCase;

    @Autowired
    private ReserveStockUseCase reserveStockUseCase;

    @Autowired
    private DeductStockUseCase deductStockUseCase;

    @Test
    void reducesReservedQuantityWithoutTouchingAvailable() {
        UUID variantId = UUID.randomUUID();
        createInventoryUseCase.create(new CreateInventoryCommand(variantId, 10));
        reserveStockUseCase.reserve(new ReserveStockCommand(variantId, 6));

        DeductStockResult result = deductStockUseCase.deduct(new DeductStockCommand(variantId, 4));

        assertThat(result.reservedQuantity()).isEqualTo(2);
        assertThat(result.availableQuantity()).isEqualTo(4);
    }

    @Test
    void rejectsDeductingMoreThanIsReserved() {
        UUID variantId = UUID.randomUUID();
        createInventoryUseCase.create(new CreateInventoryCommand(variantId, 10));
        reserveStockUseCase.reserve(new ReserveStockCommand(variantId, 3));

        assertThatThrownBy(() -> deductStockUseCase.deduct(new DeductStockCommand(variantId, 4)))
                .isInstanceOf(InsufficientReservedStockException.class);
    }

    @Test
    void rejectsANonPositiveQuantity() {
        UUID variantId = UUID.randomUUID();
        createInventoryUseCase.create(new CreateInventoryCommand(variantId, 10));
        reserveStockUseCase.reserve(new ReserveStockCommand(variantId, 3));

        assertThatThrownBy(() -> deductStockUseCase.deduct(new DeductStockCommand(variantId, 0)))
                .isInstanceOf(InvalidQuantityException.class);
    }

    @Test
    void throwsWhenNoInventoryExistsForTheVariant() {
        assertThatThrownBy(() -> deductStockUseCase.deduct(new DeductStockCommand(UUID.randomUUID(), 1)))
                .isInstanceOf(InventoryNotFoundException.class);
    }
}
