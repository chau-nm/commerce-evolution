package dev.chaunm.commerceevolution.catalog.application.usecase.media.addmedia;

import java.util.UUID;

public record AddMediaResult(
        UUID productId,
        UUID mediaId,
        String url,
        int sortOrder,
        boolean primary
) {}
