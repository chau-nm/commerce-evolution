package dev.chaunm.commerceevolution.catalog.application.usecase.deleteproduct;

import java.time.Instant;
import java.util.UUID;

public record DeleteProductResult(UUID id, Instant deletedAt) {
}
