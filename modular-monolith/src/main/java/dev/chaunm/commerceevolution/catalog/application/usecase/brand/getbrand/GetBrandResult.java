package dev.chaunm.commerceevolution.catalog.application.usecase.brand.getbrand;

import java.time.Instant;
import java.util.UUID;

public record GetBrandResult(
        UUID id,
        String name,
        Instant deletedAt
) {}
