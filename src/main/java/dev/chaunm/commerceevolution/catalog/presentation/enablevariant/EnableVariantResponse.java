package dev.chaunm.commerceevolution.catalog.presentation.enablevariant;

import java.util.UUID;

public record EnableVariantResponse(
        UUID productId,
        UUID variantId,
        boolean active
) {}
