package dev.chaunm.commerceevolution.catalog.presentation.product.assigncategory;

import java.util.UUID;

public record AssignCategoryResponse(
        UUID productId,
        UUID categoryId
) {}
