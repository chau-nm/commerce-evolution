package dev.chaunm.commerceevolution.catalog.application.usecase.category.createcategory;

import java.util.UUID;

public record CreateCategoryResult(
        UUID id,
        String name,
        UUID parentId
) {}
