package dev.chaunm.commerceevolution.inventory.application.usecase.reservestock;

public interface ReserveStockUseCase {
    ReserveStockResult reserve(ReserveStockCommand command);
}
