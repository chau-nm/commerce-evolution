package dev.chaunm.commerceevolution.catalog.application.usecase.product.duplicateproduct;

public interface DuplicateProductUseCase {
    DuplicateProductResult duplicate(DuplicateProductCommand command);
}
