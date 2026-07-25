package dev.chaunm.commerceevolution.catalog.application.usecase.category.deletecategory;

import java.time.Instant;
import java.util.UUID;

public record DeleteCategoryResult(UUID id, Instant deletedAt) {
}
