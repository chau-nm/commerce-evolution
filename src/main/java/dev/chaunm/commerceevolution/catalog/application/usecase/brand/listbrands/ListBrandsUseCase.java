package dev.chaunm.commerceevolution.catalog.application.usecase.brand.listbrands;

import dev.chaunm.commerceevolution.shared.application.pagination.PaginationResult;

public interface ListBrandsUseCase {
    PaginationResult<BrandSummaryItem> list(ListBrandsCommand command);
}
