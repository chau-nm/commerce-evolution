package dev.chaunm.commerceevolution.catalog.application.usecase.category.listcategories;

import java.util.UUID;

public record CategorySummaryItem(
        UUID id,
        String name,
        UUID parentId
) {}
