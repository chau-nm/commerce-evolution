package dev.chaunm.commerceevolution.catalog.presentation.product.createproduct;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateProductRequest(
        @NotBlank
        @Size(max = 255)
        String name,
        @NotBlank
        @Size(max = 255)
        String slug,
        UUID categoryId,
        @NotNull
        UUID brandId
) {}
