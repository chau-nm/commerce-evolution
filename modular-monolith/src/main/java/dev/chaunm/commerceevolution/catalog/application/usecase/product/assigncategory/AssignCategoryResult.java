package dev.chaunm.commerceevolution.catalog.application.usecase.product.assigncategory;

import java.util.UUID;

public record AssignCategoryResult(
        UUID productId,
        UUID categoryId
) {}
