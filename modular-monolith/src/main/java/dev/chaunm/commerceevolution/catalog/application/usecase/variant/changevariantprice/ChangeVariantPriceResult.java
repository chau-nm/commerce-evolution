package dev.chaunm.commerceevolution.catalog.application.usecase.variant.changevariantprice;

import java.util.UUID;

public record ChangeVariantPriceResult(
        UUID productId,
        UUID variantId,
        String sku,
        String name,
        long price
) {}
