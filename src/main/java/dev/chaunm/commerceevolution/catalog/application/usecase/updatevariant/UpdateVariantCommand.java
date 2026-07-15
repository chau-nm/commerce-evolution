package dev.chaunm.commerceevolution.catalog.application.usecase.updatevariant;

import java.util.UUID;

public record UpdateVariantCommand(
        UUID productId,
        UUID variantId,
        String sku,
        String name
) {}
