package dev.chaunm.commerceevolution.catalog.presentation.media.addmedia;

import java.util.UUID;

public record AddMediaResponse(
        UUID productId,
        UUID mediaId,
        String url,
        int sortOrder,
        boolean primary
) {}
