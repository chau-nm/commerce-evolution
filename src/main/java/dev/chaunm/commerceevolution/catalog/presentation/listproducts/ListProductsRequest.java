package dev.chaunm.commerceevolution.catalog.presentation.listproducts;

import dev.chaunm.commerceevolution.shared.presentation.pagination.PaginationRequest;

public record ListProductsRequest(
        String status,
        PaginationRequest pagination
) {
}
