package dev.chaunm.commerceevolution.catalog.application.usecase.media.updatemedia;

import java.util.UUID;

public record UpdateMediaResult(
        UUID productId,
        UUID mediaId,
        String url,
        int sortOrder,
        boolean primary
) {}
