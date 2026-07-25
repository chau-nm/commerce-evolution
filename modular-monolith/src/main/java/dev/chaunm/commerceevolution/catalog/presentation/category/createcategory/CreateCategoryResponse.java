package dev.chaunm.commerceevolution.catalog.presentation.category.createcategory;

import java.util.UUID;

public record CreateCategoryResponse(
        UUID id,
        String name,
        UUID parentId
) {}
