package dev.chaunm.commerceevolution.catalog.application.usecase.removemedia;

import java.util.UUID;

public record RemoveMediaCommand(
        UUID productId,
        UUID mediaId
) {}
