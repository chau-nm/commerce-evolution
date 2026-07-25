package dev.chaunm.commerceevolution.catalog.application.usecase.variant.getvariantdetail;

import java.util.UUID;

public record GetVariantDetailCommand(
        UUID productId,
        UUID variantId
) {}
