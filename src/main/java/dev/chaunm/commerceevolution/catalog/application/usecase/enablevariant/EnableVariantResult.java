package dev.chaunm.commerceevolution.catalog.application.usecase.enablevariant;

import java.util.UUID;

public record EnableVariantResult(
        UUID productId,
        UUID variantId,
        boolean active
) {}
