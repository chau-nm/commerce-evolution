package dev.chaunm.commerceevolution.catalog.application.usecase.brand.updatebrand;

import java.util.UUID;

public record UpdateBrandResult(
        UUID id,
        String name
) {}
