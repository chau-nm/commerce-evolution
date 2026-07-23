package dev.chaunm.commerceevolution.catalog.presentation.media.updatemedia;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateMediaRequest(
        @NotBlank
        @Size(max = 1024)
        String url
) {}
