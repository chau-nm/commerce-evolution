package dev.chaunm.commerceevolution.catalog.application.usecase.category.listcategories;

import dev.chaunm.commerceevolution.shared.presentation.pagination.PaginationRequest;

import java.util.UUID;

public record ListCategoriesCommand(UUID parentId, PaginationRequest pagination) {
}
