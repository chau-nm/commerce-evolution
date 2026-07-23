package dev.chaunm.commerceevolution.catalog.application.usecase.category.movecategory;

import java.util.UUID;

public record MoveCategoryCommand(
        UUID id,
        UUID parentId
) {}
