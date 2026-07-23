package dev.chaunm.commerceevolution.catalog.application.usecase.media.setthumbnail;

import java.util.UUID;

public record SetThumbnailResult(
        UUID productId,
        UUID mediaId,
        boolean primary
) {}
