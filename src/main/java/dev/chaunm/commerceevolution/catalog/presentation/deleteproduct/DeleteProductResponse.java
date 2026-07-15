package dev.chaunm.commerceevolution.catalog.presentation.deleteproduct;

import java.time.Instant;
import java.util.UUID;

public record DeleteProductResponse(UUID id, Instant deletedAt) {
}
