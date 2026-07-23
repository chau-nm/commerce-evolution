package dev.chaunm.commerceevolution.catalog.presentation.brand.getbrand;

import java.time.Instant;
import java.util.UUID;

public record GetBrandResponse(
        UUID id,
        String name,
        Instant deletedAt
) {}
