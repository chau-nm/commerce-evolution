package dev.chaunm.commerceevolution.catalog.application.usecase.category.updatecategory;

import java.util.UUID;

public record UpdateCategoryCommand(
        UUID id,
        String name
) {}
