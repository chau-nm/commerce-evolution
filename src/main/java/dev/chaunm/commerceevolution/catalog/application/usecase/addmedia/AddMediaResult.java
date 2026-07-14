package dev.chaunm.commerceevolution.catalog.application.usecase.addmedia;

import java.util.UUID;

public record AddMediaResult(
        UUID productId,
        UUID mediaId,
        String url,
        int sortOrder,
        boolean primary
) {}
