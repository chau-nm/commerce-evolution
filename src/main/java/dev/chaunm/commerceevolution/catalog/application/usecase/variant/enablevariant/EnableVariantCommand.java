package dev.chaunm.commerceevolution.catalog.application.usecase.variant.enablevariant;

import java.util.UUID;

public record EnableVariantCommand(
        UUID productId,
        UUID variantId
) {}
