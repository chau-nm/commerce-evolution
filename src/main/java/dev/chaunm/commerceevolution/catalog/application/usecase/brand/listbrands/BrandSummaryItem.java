package dev.chaunm.commerceevolution.catalog.application.usecase.brand.listbrands;

import java.util.UUID;

public record BrandSummaryItem(
        UUID id,
        String name
) {}
