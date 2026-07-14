package dev.chaunm.commerceevolution.catalog.application.usecase.changebrand;

import java.util.UUID;

public record ChangeBrandCommand(
        UUID productId,
        UUID brandId
) {}
