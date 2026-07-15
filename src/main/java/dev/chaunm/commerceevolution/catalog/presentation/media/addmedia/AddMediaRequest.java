package dev.chaunm.commerceevolution.catalog.presentation.media.addmedia;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddMediaRequest(
        @NotBlank
        @Size(max = 1024)
        String url,
        boolean primary
) {}
