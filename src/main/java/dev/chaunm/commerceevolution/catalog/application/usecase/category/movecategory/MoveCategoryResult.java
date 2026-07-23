package dev.chaunm.commerceevolution.catalog.application.usecase.category.movecategory;

import java.util.UUID;

public record MoveCategoryResult(
        UUID id,
        UUID parentId
) {}
