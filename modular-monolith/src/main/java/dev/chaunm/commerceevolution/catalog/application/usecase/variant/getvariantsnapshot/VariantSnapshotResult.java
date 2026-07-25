package dev.chaunm.commerceevolution.catalog.application.usecase.variant.getvariantsnapshot;

import java.util.UUID;

public record VariantSnapshotResult(
        UUID variantId,
        UUID productId,
        String productName,
        String variantName,
        long price,
        boolean purchasable
) {}
