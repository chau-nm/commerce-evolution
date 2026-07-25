package dev.chaunm.commerceevolution.catalog.application.usecase.media.uploadmedia;

import java.util.UUID;

public record UploadMediaCommand(
        UUID productId,
        String filename,
        byte[] content,
        String contentType,
        boolean primary
) {}
