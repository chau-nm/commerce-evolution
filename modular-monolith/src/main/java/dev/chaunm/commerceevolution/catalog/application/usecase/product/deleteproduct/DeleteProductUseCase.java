package dev.chaunm.commerceevolution.catalog.application.usecase.product.deleteproduct;

public interface DeleteProductUseCase {
    DeleteProductResult delete(DeleteProductCommand command);
}
