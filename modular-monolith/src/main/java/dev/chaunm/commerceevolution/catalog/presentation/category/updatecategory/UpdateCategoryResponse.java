package dev.chaunm.commerceevolution.catalog.presentation.category.updatecategory;

import java.util.UUID;

public record UpdateCategoryResponse(
        UUID id,
        String name
) {}
