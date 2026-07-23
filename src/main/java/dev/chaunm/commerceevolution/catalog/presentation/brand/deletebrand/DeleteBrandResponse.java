package dev.chaunm.commerceevolution.catalog.presentation.brand.deletebrand;

import java.time.Instant;
import java.util.UUID;

public record DeleteBrandResponse(UUID id, Instant deletedAt) {
}
