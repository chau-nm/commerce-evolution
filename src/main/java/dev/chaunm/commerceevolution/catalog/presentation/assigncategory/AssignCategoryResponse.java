package dev.chaunm.commerceevolution.catalog.presentation.assigncategory;

import java.util.UUID;

public record AssignCategoryResponse(
        UUID productId,
        UUID categoryId
) {}
