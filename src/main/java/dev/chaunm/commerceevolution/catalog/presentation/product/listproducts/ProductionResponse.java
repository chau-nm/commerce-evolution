package dev.chaunm.commerceevolution.catalog.presentation.product.listproducts;

import java.util.UUID;

public record ProductionResponse(
        UUID id,
        String name,
        String slug,
        UUID categoryId,
        UUID brandId,
        String status
) {
}
