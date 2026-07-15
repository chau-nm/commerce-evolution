package dev.chaunm.commerceevolution.catalog.presentation.variant.getvariantdetail;

import java.util.UUID;

public record GetVariantDetailResponse(
        UUID id,
        UUID productId,
        String sku,
        String name,
        boolean active
) {}
