package dev.chaunm.commerceevolution.catalog.presentation.category.listcategories;

import java.util.UUID;

public record CategorySummaryResponse(
        UUID id,
        String name,
        UUID parentId
) {}
