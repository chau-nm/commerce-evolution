package dev.chaunm.commerceevolution.catalog.presentation.brand.createbrand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateBrandRequest(
        @NotBlank
        @Size(max = 255)
        String name
) {}
