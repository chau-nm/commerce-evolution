package dev.chaunm.commerceevolution.catalog.application.usecase.media.reordermedia;

import java.util.List;
import java.util.UUID;

public record ReorderMediaResult(
        UUID productId,
        List<MediaItem> medias
) {
    public record MediaItem(UUID id, String url, int sortOrder, boolean primary) {
    }
}
