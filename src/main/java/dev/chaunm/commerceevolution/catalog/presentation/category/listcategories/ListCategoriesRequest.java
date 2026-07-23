package dev.chaunm.commerceevolution.catalog.presentation.category.listcategories;

import dev.chaunm.commerceevolution.shared.presentation.pagination.PaginationRequest;

import java.util.UUID;

public record ListCategoriesRequest(
        UUID parentId,
        PaginationRequest pagination
) {
}
