package dev.chaunm.commerceevolution.inventory.application.usecase.deductstock;

public interface DeductStockUseCase {
    DeductStockResult deduct(DeductStockCommand command);
}
