package dev.chaunm.commerceevolution.catalog.presentation.media.reordermedia;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

public record ReorderMediaRequest(
        @NotEmpty
        List<UUID> mediaIds
) {}
