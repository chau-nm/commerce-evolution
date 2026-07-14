package dev.chaunm.commerceevolution.catalog.presentation.changebrand;

import java.util.UUID;

public record ChangeBrandResponse(
        UUID productId,
        UUID brandId
) {}
