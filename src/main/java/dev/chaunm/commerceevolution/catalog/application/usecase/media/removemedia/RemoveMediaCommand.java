package dev.chaunm.commerceevolution.catalog.application.usecase.media.removemedia;

import java.util.UUID;

public record RemoveMediaCommand(
        UUID productId,
        UUID mediaId
) {}
