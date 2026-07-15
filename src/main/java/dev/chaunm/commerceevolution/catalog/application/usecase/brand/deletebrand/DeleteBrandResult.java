package dev.chaunm.commerceevolution.catalog.application.usecase.brand.deletebrand;

import java.time.Instant;
import java.util.UUID;

public record DeleteBrandResult(UUID id, Instant deletedAt) {
}
