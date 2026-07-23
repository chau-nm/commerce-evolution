package dev.chaunm.commerceevolution.catalog.presentation.variant.updatevariant;

import java.util.UUID;

public record UpdateVariantResponse(
        UUID productId,
        UUID variantId,
        String sku,
        String name,
        boolean active
) {}
