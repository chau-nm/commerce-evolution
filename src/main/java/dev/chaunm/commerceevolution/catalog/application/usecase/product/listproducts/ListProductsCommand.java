package dev.chaunm.commerceevolution.catalog.application.usecase.product.listproducts;

import dev.chaunm.commerceevolution.shared.presentation.pagination.PaginationRequest;

public record ListProductsCommand(String status, PaginationRequest pagination) {
}
