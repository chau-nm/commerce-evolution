package dev.chaunm.commerceevolution.catalog.application.usecase.category.listcategories;

import dev.chaunm.commerceevolution.shared.application.pagination.PaginationResult;

public interface ListCategoriesUseCase {
    PaginationResult<CategorySummaryItem> list(ListCategoriesCommand command);
}
