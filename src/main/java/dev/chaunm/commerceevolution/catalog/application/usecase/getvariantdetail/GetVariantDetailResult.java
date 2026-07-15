package dev.chaunm.commerceevolution.catalog.application.usecase.getvariantdetail;

import java.util.UUID;

public record GetVariantDetailResult(
        UUID id,
        UUID productId,
        String sku,
        String name,
        boolean active
) {}
