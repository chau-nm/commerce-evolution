package dev.chaunm.commerceevolution.catalog.application.usecase.product.changebrand;

import java.util.UUID;

public record ChangeBrandCommand(
        UUID productId,
        UUID brandId
) {}
