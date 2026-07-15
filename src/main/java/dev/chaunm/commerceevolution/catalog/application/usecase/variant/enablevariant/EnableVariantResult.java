package dev.chaunm.commerceevolution.catalog.application.usecase.variant.enablevariant;

import java.util.UUID;

public record EnableVariantResult(
        UUID productId,
        UUID variantId,
        boolean active
) {}
