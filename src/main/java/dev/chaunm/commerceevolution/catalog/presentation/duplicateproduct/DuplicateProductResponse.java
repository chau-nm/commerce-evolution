package dev.chaunm.commerceevolution.catalog.presentation.duplicateproduct;

import java.util.UUID;

public record DuplicateProductResponse(UUID id, UUID sourceId, String name, String slug, String status) {
}
