package dev.chaunm.commerceevolution.catalog.presentation.media.reordermedia;

import java.util.List;
import java.util.UUID;

public record ReorderMediaResponse(
        UUID productId,
        List<MediaItemResponse> medias
) {
    public record MediaItemResponse(UUID id, String url, int sortOrder, boolean primary) {
    }
}
