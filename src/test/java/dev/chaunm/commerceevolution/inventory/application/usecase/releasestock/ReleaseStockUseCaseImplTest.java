package dev.chaunm.commerceevolution.inventory.application.usecase.releasestock;

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
class ReleaseStockUseCaseImplTest {

    @Autowired
    private CreateInventoryUseCase createInventoryUseCase;

    @Autowired
    private ReserveStockUseCase reserveStockUseCase;

    @Autowired
    private ReleaseStockUseCase releaseStockUseCase;

    @Test
    void movesQuantityFromReservedBackToAvailable() {
        UUID variantId = UUID.randomUUID();
        createInventoryUseCase.create(new CreateInventoryCommand(variantId, 10));
        reserveStockUseCase.reserve(new ReserveStockCommand(variantId, 6));

        ReleaseStockResult result = releaseStockUseCase.release(new ReleaseStockCommand(variantId, 4));

        assertThat(result.availableQuantity()).isEqualTo(8);
        assertThat(result.reservedQuantity()).isEqualTo(2);
    }

    @Test
    void rejectsReleasingMoreThanIsReserved() {
        UUID variantId = UUID.randomUUID();
        createInventoryUseCase.create(new CreateInventoryCommand(variantId, 10));
        reserveStockUseCase.reserve(new ReserveStockCommand(variantId, 3));

        assertThatThrownBy(() -> releaseStockUseCase.release(new ReleaseStockCommand(variantId, 4)))
                .isInstanceOf(InsufficientReservedStockException.class);
    }

    @Test
    void rejectsANonPositiveQuantity() {
        UUID variantId = UUID.randomUUID();
        createInventoryUseCase.create(new CreateInventoryCommand(variantId, 10));
        reserveStockUseCase.reserve(new ReserveStockCommand(variantId, 3));

        assertThatThrownBy(() -> releaseStockUseCase.release(new ReleaseStockCommand(variantId, 0)))
                .isInstanceOf(InvalidQuantityException.class);
    }

    @Test
    void throwsWhenNoInventoryExistsForTheVariant() {
        assertThatThrownBy(() -> releaseStockUseCase.release(new ReleaseStockCommand(UUID.randomUUID(), 1)))
                .isInstanceOf(InventoryNotFoundException.class);
    }
}
