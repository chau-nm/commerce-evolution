package dev.chaunm.commerceevolution.catalog.presentation.variant.changevariantprice;

import jakarta.validation.constraints.Positive;

public record ChangeVariantPriceRequest(
        @Positive
        long price
) {}
