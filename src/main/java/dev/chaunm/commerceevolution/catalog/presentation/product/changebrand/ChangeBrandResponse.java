package dev.chaunm.commerceevolution.catalog.presentation.product.changebrand;

import java.util.UUID;

public record ChangeBrandResponse(
        UUID productId,
        UUID brandId
) {}
