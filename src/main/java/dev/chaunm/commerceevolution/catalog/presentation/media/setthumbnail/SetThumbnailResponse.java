package dev.chaunm.commerceevolution.catalog.presentation.media.setthumbnail;

import java.util.UUID;

public record SetThumbnailResponse(
        UUID productId,
        UUID mediaId,
        boolean primary
) {}
