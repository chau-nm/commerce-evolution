package dev.chaunm.commerceevolution.inventory.application.usecase.adjuststock;

public interface AdjustStockUseCase {
    AdjustStockResult adjust(AdjustStockCommand command);
}
