package dev.chaunm.commerceevolution.catalog.application.usecase.media.reordermedia;

import java.util.List;
import java.util.UUID;

public record ReorderMediaCommand(
        UUID productId,
        List<UUID> mediaIds
) {}
