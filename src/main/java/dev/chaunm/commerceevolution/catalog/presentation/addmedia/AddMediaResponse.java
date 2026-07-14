package dev.chaunm.commerceevolution.catalog.presentation.addmedia;

import java.util.UUID;

public record AddMediaResponse(
        UUID productId,
        UUID mediaId,
        String url,
        int sortOrder,
        boolean primary
) {}
