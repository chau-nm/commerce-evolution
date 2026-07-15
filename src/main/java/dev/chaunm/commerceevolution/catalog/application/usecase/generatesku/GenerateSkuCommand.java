package dev.chaunm.commerceevolution.catalog.application.usecase.generatesku;

import java.util.UUID;

public record GenerateSkuCommand(
        UUID productId,
        String variantName
) {}
