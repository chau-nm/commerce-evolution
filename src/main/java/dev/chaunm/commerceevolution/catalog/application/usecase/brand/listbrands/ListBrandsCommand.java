package dev.chaunm.commerceevolution.catalog.application.usecase.brand.listbrands;

import dev.chaunm.commerceevolution.shared.presentation.pagination.PaginationRequest;

public record ListBrandsCommand(PaginationRequest pagination) {
}
