package dev.chaunm.commerceevolution.catalog.presentation.category.movecategory;

import java.util.UUID;

public record MoveCategoryResponse(
        UUID id,
        UUID parentId
) {}
