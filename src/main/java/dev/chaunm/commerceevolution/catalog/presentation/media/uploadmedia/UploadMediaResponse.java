package dev.chaunm.commerceevolution.catalog.presentation.media.uploadmedia;

import java.util.UUID;

public record UploadMediaResponse(
        UUID productId,
        UUID mediaId,
        String url,
        int sortOrder,
        boolean primary
) {}
