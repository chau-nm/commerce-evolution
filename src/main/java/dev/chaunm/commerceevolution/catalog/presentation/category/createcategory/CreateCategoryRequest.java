package dev.chaunm.commerceevolution.catalog.presentation.category.createcategory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateCategoryRequest(
        @NotBlank
        @Size(max = 255)
        String name,
        UUID parentId
) {}
