package dev.chaunm.commerceevolution.catalog.application.usecase.media.uploadmedia;

import java.util.UUID;

public record UploadMediaResult(
        UUID productId,
        UUID mediaId,
        String url,
        int sortOrder,
        boolean primary
) {}
