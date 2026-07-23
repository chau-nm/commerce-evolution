package dev.chaunm.commerceevolution.catalog.application.usecase.media.setthumbnail;

import java.util.UUID;

public record SetThumbnailCommand(
        UUID productId,
        UUID mediaId
) {}
