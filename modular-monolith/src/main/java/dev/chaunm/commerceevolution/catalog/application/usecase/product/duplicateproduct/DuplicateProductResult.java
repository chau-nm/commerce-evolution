package dev.chaunm.commerceevolution.catalog.application.usecase.product.duplicateproduct;

import java.util.UUID;

public record DuplicateProductResult(UUID id, UUID sourceId, String name, String slug, String status) {
}
