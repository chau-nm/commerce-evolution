package dev.chaunm.commerceevolution.catalog.application.usecase.variant.disablevariant;

import java.util.UUID;

public record DisableVariantResult(
        UUID productId,
        UUID variantId,
        boolean active
) {}
