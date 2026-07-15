package dev.chaunm.commerceevolution.catalog.application.usecase.listproducts;

import dev.chaunm.commerceevolution.shared.application.pagination.PaginationResult;

public interface ListProductsUseCase {
    PaginationResult<ProductSummaryItem> list(ListProductsCommand command);
}
