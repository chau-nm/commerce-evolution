package dev.chaunm.commerceevolution.catalog.application.usecase.variant.changevariantprice;

import java.util.UUID;

public record ChangeVariantPriceCommand(
        UUID productId,
        UUID variantId,
        long price
) {}
