package dev.chaunm.commerceevolution.catalog.presentation.variant.updatevariant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateVariantRequest(
        @NotBlank
        @Size(max = 64)
        String sku,
        @NotBlank
        @Size(max = 255)
        String name
) {}
