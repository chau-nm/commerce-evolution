package dev.chaunm.commerceevolution.catalog.presentation.category.deletecategory;

import java.time.Instant;
import java.util.UUID;

public record DeleteCategoryResponse(UUID id, Instant deletedAt) {
}
