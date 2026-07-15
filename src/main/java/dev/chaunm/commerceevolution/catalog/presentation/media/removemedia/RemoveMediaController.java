package dev.chaunm.commerceevolution.catalog.presentation.media.removemedia;

import dev.chaunm.commerceevolution.catalog.application.usecase.media.removemedia.RemoveMediaMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.media.removemedia.RemoveMediaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/products/{productId}/media/{mediaId}")
@RequiredArgsConstructor
public class RemoveMediaController {

    private final RemoveMediaUseCase removeMediaUseCase;
    private final RemoveMediaMapper mapper;

    @DeleteMapping
    public ResponseEntity<Void> removeMedia(
            @PathVariable UUID productId,
            @PathVariable UUID mediaId
    ) {
        removeMediaUseCase.removeMedia(mapper.toCommand(productId, mediaId));
        return ResponseEntity.noContent().build();
    }
}
