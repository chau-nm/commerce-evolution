package dev.chaunm.commerceevolution.catalog.presentation.media.updatemedia;

import java.util.UUID;

public record UpdateMediaResponse(
        UUID productId,
        UUID mediaId,
        String url,
        int sortOrder,
        boolean primary
) {}
