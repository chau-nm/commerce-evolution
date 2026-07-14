package dev.chaunm.commerceevolution.catalog.application.usecase.addvariant;

import java.util.UUID;

public record AddVariantResult(
        UUID productId,
        UUID variantId,
        String sku,
        String name
) {}
