package dev.chaunm.commerceevolution.catalog.application.usecase.category.createcategory;

import java.util.UUID;

public record CreateCategoryCommand(
        String name,
        UUID parentId
) {}
