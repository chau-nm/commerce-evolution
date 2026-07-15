package dev.chaunm.commerceevolution.catalog.presentation.disablevariant;

import java.util.UUID;

public record DisableVariantResponse(
        UUID productId,
        UUID variantId,
        boolean active
) {}
