package dev.chaunm.commerceevolution.catalog.application.usecase.variant.addvariant;

import java.util.UUID;

public record AddVariantResult(
        UUID productId,
        UUID variantId,
        String sku,
        String name,
        long price
) {}
