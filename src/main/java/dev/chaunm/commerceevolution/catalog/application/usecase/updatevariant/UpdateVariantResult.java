package dev.chaunm.commerceevolution.catalog.application.usecase.updatevariant;

import java.util.UUID;

public record UpdateVariantResult(
        UUID productId,
        UUID variantId,
        String sku,
        String name,
        boolean active
) {}
