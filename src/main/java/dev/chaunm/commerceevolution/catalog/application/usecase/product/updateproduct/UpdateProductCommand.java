package dev.chaunm.commerceevolution.catalog.application.usecase.product.updateproduct;

import java.util.UUID;

public record UpdateProductCommand(
        UUID id,
        String name,
        String slug
) {}
