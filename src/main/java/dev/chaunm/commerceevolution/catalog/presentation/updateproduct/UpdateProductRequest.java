package dev.chaunm.commerceevolution.catalog.presentation.updateproduct;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateProductRequest(
        @NotBlank
        @Size(max = 255)
        String name,
        @NotBlank
        @Size(max = 255)
        String slug
) {}
