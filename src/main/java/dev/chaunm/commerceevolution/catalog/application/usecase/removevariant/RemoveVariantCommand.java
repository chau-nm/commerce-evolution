package dev.chaunm.commerceevolution.catalog.application.usecase.removevariant;

import java.util.UUID;

public record RemoveVariantCommand(
        UUID productId,
        UUID variantId
) {}
