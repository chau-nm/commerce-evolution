package dev.chaunm.commerceevolution.inventory.domain.model;

import dev.chaunm.commerceevolution.inventory.domain.event.StockAdjustedEvent;
import dev.chaunm.commerceevolution.inventory.domain.event.StockDeductedEvent;
import dev.chaunm.commerceevolution.inventory.domain.event.StockReleasedEvent;
import dev.chaunm.commerceevolution.inventory.domain.event.StockReservedEvent;
import dev.chaunm.commerceevolution.inventory.domain.exception.InsufficientAvailableStockException;
import dev.chaunm.commerceevolution.inventory.domain.exception.InsufficientReservedStockException;
import dev.chaunm.commerceevolution.inventory.domain.exception.InvalidQuantityException;
import dev.chaunm.commerceevolution.inventory.domain.factory.InventoryFactory;
import dev.chaunm.commerceevolution.inventory.domain.model.valueobject.VariantId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InventoryTest {

    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = InventoryFactory.create(new VariantId(UUID.randomUUID()), 10);
        inventory.clearDomainEvents();
    }

    @Test
    void adjustStockIncreasesAvailableQuantityAndRegistersEvent() {
        inventory.adjustStock(5);

        assertThat(inventory.getAvailableQuantity()).isEqualTo(15);
        assertThat(inventory.domainEvents())
                .singleElement()
                .isInstanceOf(StockAdjustedEvent.class);
    }

    @Test
    void adjustStockDecreasesAvailableQuantity() {
        inventory.adjustStock(-4);

        assertThat(inventory.getAvailableQuantity()).isEqualTo(6);
    }

    @Test
    void adjustStockRejectsADeltaThatWouldMakeAvailableQuantityNegative() {
        assertThatThrownBy(() -> inventory.adjustStock(-11))
                .isInstanceOf(InvalidQuantityException.class);
        assertThat(inventory.getAvailableQuantity()).isEqualTo(10);
    }

    @Test
    void reserveMovesQuantityFromAvailableToReservedAndRegistersEvent() {
        inventory.reserve(4);

        assertThat(inventory.getAvailableQuantity()).isEqualTo(6);
        assertThat(inventory.getReservedQuantity()).isEqualTo(4);
        assertThat(inventory.domainEvents())
                .singleElement()
                .isInstanceOf(StockReservedEvent.class);
    }

    @Test
    void reserveRejectsNonPositiveQuantity() {
        assertThatThrownBy(() -> inventory.reserve(0))
                .isInstanceOf(InvalidQuantityException.class);
    }

    @Test
    void reserveRejectsAQuantityGreaterThanAvailable() {
        assertThatThrownBy(() -> inventory.reserve(11))
                .isInstanceOf(InsufficientAvailableStockException.class);
        assertThat(inventory.getAvailableQuantity()).isEqualTo(10);
        assertThat(inventory.getReservedQuantity()).isZero();
    }

    @Test
    void releaseMovesQuantityFromReservedBackToAvailableAndRegistersEvent() {
        inventory.reserve(6);
        inventory.clearDomainEvents();

        inventory.release(4);

        assertThat(inventory.getAvailableQuantity()).isEqualTo(8);
        assertThat(inventory.getReservedQuantity()).isEqualTo(2);
        assertThat(inventory.domainEvents())
                .singleElement()
                .isInstanceOf(StockReleasedEvent.class);
    }

    @Test
    void releaseRejectsNonPositiveQuantity() {
        inventory.reserve(6);

        assertThatThrownBy(() -> inventory.release(0))
                .isInstanceOf(InvalidQuantityException.class);
    }

    @Test
    void releaseRejectsAQuantityGreaterThanReserved() {
        inventory.reserve(3);

        assertThatThrownBy(() -> inventory.release(4))
                .isInstanceOf(InsufficientReservedStockException.class);
        assertThat(inventory.getReservedQuantity()).isEqualTo(3);
    }

    @Test
    void deductReducesReservedQuantityWithoutTouchingAvailableAndRegistersEvent() {
        inventory.reserve(6);
        inventory.clearDomainEvents();

        inventory.deduct(4);

        assertThat(inventory.getReservedQuantity()).isEqualTo(2);
        assertThat(inventory.getAvailableQuantity()).isEqualTo(4);
        assertThat(inventory.domainEvents())
                .singleElement()
                .isInstanceOf(StockDeductedEvent.class);
    }

    @Test
    void deductRejectsNonPositiveQuantity() {
        inventory.reserve(6);

        assertThatThrownBy(() -> inventory.deduct(0))
                .isInstanceOf(InvalidQuantityException.class);
    }

    @Test
    void deductRejectsAQuantityGreaterThanReserved() {
        inventory.reserve(3);

        assertThatThrownBy(() -> inventory.deduct(4))
                .isInstanceOf(InsufficientReservedStockException.class);
        assertThat(inventory.getReservedQuantity()).isEqualTo(3);
    }
}
