package dev.chaunm.commerceevolution.catalog.application.usecase.duplicateproduct;

public interface DuplicateProductUseCase {
    DuplicateProductResult duplicate(DuplicateProductCommand command);
}
