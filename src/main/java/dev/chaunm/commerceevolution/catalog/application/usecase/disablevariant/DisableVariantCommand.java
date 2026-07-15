package dev.chaunm.commerceevolution.catalog.application.usecase.disablevariant;

import java.util.UUID;

public record DisableVariantCommand(
        UUID productId,
        UUID variantId
) {}
