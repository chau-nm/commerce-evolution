package dev.chaunm.commerceevolution.catalog.application.usecase.category.updatecategory;

import java.util.UUID;

public record UpdateCategoryResult(
        UUID id,
        String name
) {}
