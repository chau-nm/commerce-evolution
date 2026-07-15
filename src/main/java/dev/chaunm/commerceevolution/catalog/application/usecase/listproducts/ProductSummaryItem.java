package dev.chaunm.commerceevolution.catalog.application.usecase.listproducts;

import java.util.UUID;

public record ProductSummaryItem(
        UUID id,
        String name,
        String slug,
        UUID categoryId,
        UUID brandId,
        String status
) {
}
