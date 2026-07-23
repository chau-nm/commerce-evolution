package dev.chaunm.commerceevolution.inventory.application.usecase.releasestock;

public interface ReleaseStockUseCase {
    ReleaseStockResult release(ReleaseStockCommand command);
}
