package dev.chaunm.commerceevolution.catalog.application.usecase.brand.createbrand;

import java.util.UUID;

public record CreateBrandResult(
        UUID id,
        String name
) {}
