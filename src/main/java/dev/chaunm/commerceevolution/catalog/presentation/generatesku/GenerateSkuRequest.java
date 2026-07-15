package dev.chaunm.commerceevolution.catalog.presentation.generatesku;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GenerateSkuRequest(
        @NotBlank
        @Size(max = 255)
        String variantName
) {}
