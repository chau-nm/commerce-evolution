package dev.chaunm.commerceevolution.catalog.presentation.product.assigncategory;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AssignCategoryRequest(
        @NotNull
        UUID categoryId
) {}
