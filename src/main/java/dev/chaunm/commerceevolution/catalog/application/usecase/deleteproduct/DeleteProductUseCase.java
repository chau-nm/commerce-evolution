package dev.chaunm.commerceevolution.catalog.application.usecase.deleteproduct;

public interface DeleteProductUseCase {
    DeleteProductResult delete(DeleteProductCommand command);
}
