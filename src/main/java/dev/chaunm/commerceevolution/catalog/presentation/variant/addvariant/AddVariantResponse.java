package dev.chaunm.commerceevolution.catalog.presentation.variant.addvariant;

import java.util.UUID;

public record AddVariantResponse(
        UUID productId,
        UUID variantId,
        String sku,
        String name,
        long price
) {}
