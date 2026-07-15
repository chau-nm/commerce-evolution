package dev.chaunm.commerceevolution.catalog.presentation.brand.listbrands;

import java.util.UUID;

public record BrandSummaryResponse(
        UUID id,
        String name
) {}
