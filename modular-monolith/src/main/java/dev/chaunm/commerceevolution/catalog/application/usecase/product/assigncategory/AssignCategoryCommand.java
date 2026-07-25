package dev.chaunm.commerceevolution.catalog.application.usecase.product.assigncategory;

import java.util.UUID;

public record AssignCategoryCommand(
        UUID productId,
        UUID categoryId
) {}
