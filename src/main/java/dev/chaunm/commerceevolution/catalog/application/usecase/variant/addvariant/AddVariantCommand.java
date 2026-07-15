package dev.chaunm.commerceevolution.catalog.application.usecase.variant.addvariant;

import java.util.UUID;

public record AddVariantCommand(
        UUID productId,
        String sku,
        String name
) {}
