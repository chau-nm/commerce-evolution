package dev.chaunm.commerceevolution.catalog.application.usecase.category.getcategory;

import java.time.Instant;
import java.util.UUID;

public record GetCategoryResult(
        UUID id,
        String name,
        UUID parentId,
        Instant deletedAt
) {}
