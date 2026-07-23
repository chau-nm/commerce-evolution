package dev.chaunm.commerceevolution.catalog.presentation.category.getcategory;

import java.time.Instant;
import java.util.UUID;

public record GetCategoryResponse(
        UUID id,
        String name,
        UUID parentId,
        Instant deletedAt
) {}
