package dev.chaunm.commerceevolution.catalog.presentation.addvariant;

import java.util.UUID;

public record AddVariantResponse(
        UUID productId,
        UUID variantId,
        String sku,
        String name
) {}
