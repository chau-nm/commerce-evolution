package dev.chaunm.commerceevolution.catalog.presentation.brand.updatebrand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateBrandRequest(
        @NotBlank
        @Size(max = 255)
        String name
) {}
