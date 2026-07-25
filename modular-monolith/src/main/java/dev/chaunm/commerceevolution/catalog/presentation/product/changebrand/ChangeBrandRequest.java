package dev.chaunm.commerceevolution.catalog.presentation.product.changebrand;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ChangeBrandRequest(
        @NotNull
        UUID brandId
) {}
