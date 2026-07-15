package dev.chaunm.commerceevolution.catalog.presentation.variant.changevariantprice;

import java.util.UUID;

public record ChangeVariantPriceResponse(
        UUID productId,
        UUID variantId,
        String sku,
        String name,
        long price
) {}
