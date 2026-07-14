package dev.chaunm.commerceevolution.catalog.application.usecase.createproduct;

import java.util.UUID;

public record CreateProductCommand(
        String name,
        String slug,
        UUID categoryId,
        UUID brandId
) {}
