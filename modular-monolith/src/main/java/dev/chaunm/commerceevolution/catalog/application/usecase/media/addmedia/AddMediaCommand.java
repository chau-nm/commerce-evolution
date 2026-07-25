package dev.chaunm.commerceevolution.catalog.application.usecase.media.addmedia;

import java.util.UUID;

public record AddMediaCommand(
        UUID productId,
        String url,
        boolean primary
) {}
