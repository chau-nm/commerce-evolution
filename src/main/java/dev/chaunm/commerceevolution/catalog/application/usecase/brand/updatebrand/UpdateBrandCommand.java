package dev.chaunm.commerceevolution.catalog.application.usecase.brand.updatebrand;

import java.util.UUID;

public record UpdateBrandCommand(
        UUID id,
        String name
) {}
