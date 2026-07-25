package dev.chaunm.commerceevolution.catalog.application.usecase.media.updatemedia;

import java.util.UUID;

public record UpdateMediaCommand(
        UUID productId,
        UUID mediaId,
        String url
) {}
